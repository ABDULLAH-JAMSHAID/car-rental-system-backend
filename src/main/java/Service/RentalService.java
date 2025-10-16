package Service;


import Enums.CarStatus;
import Model.Car;
import Model.Rental;
import Repository.RentalRepository;
import java.sql.SQLException;


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
    }
}
