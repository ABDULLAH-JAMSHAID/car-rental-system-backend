package DTO.RentalDTO;

public class AdminDashboardDTO {
    private int totalCars;
    private int totalUsers;
    private int totalRentals;
    private int activeRentals;
    private int overdueRentals;
    private double totalRevenue;

    private String mostRentedCar;
    private String topRatedCar;

    // Getters and Setters
    public int getTotalCars() { return totalCars; }
    public void setTotalCars(int totalCars) { this.totalCars = totalCars; }

    public int getTotalUsers() { return totalUsers; }
    public void setTotalUsers(int totalUsers) { this.totalUsers = totalUsers; }

    public int getTotalRentals() { return totalRentals; }
    public void setTotalRentals(int totalRentals) { this.totalRentals = totalRentals; }

    public int getActiveRentals() { return activeRentals; }
    public void setActiveRentals(int activeRentals) { this.activeRentals = activeRentals; }

    public int getOverdueRentals() { return overdueRentals; }
    public void setOverdueRentals(int overdueRentals) { this.overdueRentals = overdueRentals; }

    public double getTotalRevenue() { return totalRevenue; }
    public void setTotalRevenue(double totalRevenue) { this.totalRevenue = totalRevenue; }

    public String getMostRentedCar() { return mostRentedCar; }
    public void setMostRentedCar(String mostRentedCar) { this.mostRentedCar = mostRentedCar; }

    public String getTopRatedCar() { return topRatedCar; }
    public void setTopRatedCar(String topRatedCar) { this.topRatedCar = topRatedCar; }
}
