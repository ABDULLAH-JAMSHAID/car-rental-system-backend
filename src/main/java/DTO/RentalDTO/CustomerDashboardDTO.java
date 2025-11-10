package DTO.RentalDTO;

public class CustomerDashboardDTO {
    private int totalRentalsMade;
    private int activeRentalsCount;
    private double totalAmountSpent;

    private String lastCarName;
    private String lastDropoffDate;
    private double lastCarPricePerDay;

    // Getters and setters
    public int getTotalRentalsMade() { return totalRentalsMade; }
    public void setTotalRentalsMade(int totalRentalsMade) { this.totalRentalsMade = totalRentalsMade; }

    public int getActiveRentalsCount() { return activeRentalsCount; }
    public void setActiveRentalsCount(int activeRentalsCount) { this.activeRentalsCount = activeRentalsCount; }

    public double getTotalAmountSpent() { return totalAmountSpent; }
    public void setTotalAmountSpent(double totalAmountSpent) { this.totalAmountSpent = totalAmountSpent; }

    public String getLastCarName() { return lastCarName; }
    public void setLastCarName(String lastCarName) { this.lastCarName = lastCarName; }

    public String getLastDropoffDate() { return lastDropoffDate; }
    public void setLastDropoffDate(String lastDropoffDate) { this.lastDropoffDate = lastDropoffDate; }

    public double getLastCarPricePerDay() { return lastCarPricePerDay; }
    public void setLastCarPricePerDay(double lastCarPricePerDay) { this.lastCarPricePerDay = lastCarPricePerDay; }
}
