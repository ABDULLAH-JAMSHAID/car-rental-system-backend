package Service;


import DTO.RentalDTO.CancelRequestDTO;
import DTO.RentalDTO.CancelResponseDTO;
import DTO.RentalDTO.ReturnSummaryDTO;
import Enums.CarStatus;
import Handler.AppException;
import Model.Car;
import Model.Rental;
import Repository.RentalRepository;
import jakarta.servlet.http.HttpServletResponse;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;


public class RentalService {

    private final RentalRepository rentalRepo = new RentalRepository();

    public void bookRental(Rental rental) throws Exception {
        // Validate dates
        if (rental.getPickupDate().after(rental.getDropoffDate())) {
            throw new IllegalArgumentException("Drop-off date must be after pickup date.");
        }

        // Check car availability
        Car car= rentalRepo.findCarById(rental.getCarId());

        if (!car.getStatus().equals(CarStatus.AVAILABLE)) {
            throw new IllegalStateException("Car is not available for the Rent");
        }

        // Calculate total days
        long diffMillis = rental.getDropoffDate().getTime() - rental.getPickupDate().getTime();
        int totalDays = (int) (diffMillis / (1000 * 60 * 60 * 24));
        rental.setTotalDays(totalDays);

        // Calculate price
        double pricePerDay = car.getPrize_per_day();
        rental.setTotalPrice(pricePerDay * totalDays);

        // Set default status

        rental.setStatus("PENDING");

        // Save to DB

        boolean inserted = rentalRepo.insertRental(rental);
        if (!inserted) {
            throw new SQLException("Failed to insert rental record.");
        }
        rentalRepo.updateCarStatus(car.getId(), CarStatus.RESERVED);
    }

    public boolean updateRentalStatus(int rentalId, CarStatus status) {

        boolean checkRentalExists = rentalRepo.checkRentalExists(rentalId);
        if (!checkRentalExists) {
            return false;
        }
        if (status==CarStatus.APPROVED){
            rentalRepo.updateRentalStatus(rentalId,CarStatus.APPROVED);
            rentalRepo.updateCarStatus(rentalId,CarStatus.RENTED);
            return true;
        }
        if (status==CarStatus.REJECTED){
            rentalRepo.updateRentalStatus(rentalId,CarStatus.REJECTED);
            rentalRepo.updateCarStatus(rentalId,CarStatus.AVAILABLE);
            return true;
        }
        if (status==CarStatus.RETURNED){
            rentalRepo.updateRentalStatus(rentalId,CarStatus.RETURNED);
            rentalRepo.updateCarStatus(rentalId,CarStatus.AVAILABLE);
            return true;
        }

        return false;

    }

    public ReturnSummaryDTO returnCar(int rentalId) {
        Rental rental = rentalRepo.getRentalById(rentalId);
        if (rental == null) {
            throw new AppException(HttpServletResponse.SC_NOT_FOUND, "Rental not found");
        }

        Timestamp actualReturnTime = new Timestamp(System.currentTimeMillis());
        Timestamp expectedDropoff = rental.getDropoffDate();

        // ✅ Calculate late hours safely (never negative)
        long diffMillis = actualReturnTime.getTime() - expectedDropoff.getTime();
        long lateHours = Math.max(0, diffMillis / (1000 * 60 * 60));

        // ✅ Fine only if late
        double fineAmount = 0;
        if (lateHours > 0) {
            fineAmount = lateHours * 500; // example: 500 Rs/hour late
            rentalRepo.addFine(rentalId, fineAmount, "Late return by " + lateHours + " hours");
        }

        rentalRepo.updateRentalStatus(rentalId, CarStatus.RETURNED);

        // ✅ Build response
        ReturnSummaryDTO summary = new ReturnSummaryDTO();
        summary.setRentalId(rentalId);
        summary.setStatus("RETURNED");
        summary.setLateReturn(lateHours > 0);
        summary.setFineAmount(fineAmount);
        summary.setLateHours(lateHours);
        summary.setMessage(lateHours > 0
                ? "Car returned late. Fine applied."
                : "Car returned successfully on time.");

        return summary;
    }

    public CancelResponseDTO cancelBooking(CancelRequestDTO request) {
        int rentalId = request.getRentalId();
        String reason = request.getCancelReason();

        Rental rental = rentalRepo.getRentalById(rentalId);
        if (rental == null) {
            throw new AppException(404, "Rental not found.");
        }

        if (rental.getStatus().equalsIgnoreCase("CANCELLED") ||
                rental.getStatus().equalsIgnoreCase("RETURNED")) {
            throw new AppException(400, "Booking already cancelled or completed.");
        }

        // ✅ Always use same timezone for both
        ZoneId zone = ZoneId.of("Asia/Karachi");
        LocalDateTime now = LocalDateTime.now(zone);
        LocalDateTime pickup = rental.getPickupDate()
                .toInstant()
                .atZone(zone)
                .toLocalDateTime();


        
        if (pickup.isBefore(now)) {
            throw new AppException(400, "Cannot cancel. Pickup time has already passed.");
        }

        long hoursDiff = Duration.between(now, pickup).toHours();
        System.out.println("HOURS_DIFF: " + hoursDiff);

        if (hoursDiff < 2) {
            throw new AppException(400, "Cannot cancel booking. Less than 2 hours remaining before pickup.");
        }

        rentalRepo.cancelRental(rentalId);

        return new CancelResponseDTO(
                rentalId,
                CarStatus.CANCELLED,
                "Booking cancelled successfully. Reason: " + (reason == null ? "No reason provided." : reason)
        );
    }


}
