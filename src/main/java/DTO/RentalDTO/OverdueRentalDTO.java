package DTO.RentalDTO;

import java.time.LocalDate;

public class OverdueRentalDTO {
    private int rentalId;
    private String carName;
    private String userName;
    private LocalDate dropOffDate;
    private int daysOverdue;
    private double estimatedPenalty;

    // Getters & Setters
    public int getRentalId() { return rentalId; }
    public void setRentalId(int rentalId) { this.rentalId = rentalId; }

    public String getCarName() { return carName; }
    public void setCarName(String carName) { this.carName = carName; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public LocalDate getDropOffDate() { return dropOffDate; }
    public void setDropOffDate(LocalDate dropOffDate) { this.dropOffDate = dropOffDate; }

    public int getDaysOverdue() { return daysOverdue; }
    public void setDaysOverdue(int daysOverdue) { this.daysOverdue = daysOverdue; }

    public double getEstimatedPenalty() { return estimatedPenalty; }
    public void setEstimatedPenalty(double estimatedPenalty) { this.estimatedPenalty = estimatedPenalty; }
}
