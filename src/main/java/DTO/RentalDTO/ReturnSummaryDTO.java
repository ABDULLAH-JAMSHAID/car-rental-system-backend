package DTO.RentalDTO;

import java.math.BigDecimal;

public class ReturnSummaryDTO {
    private int rentalId;
    private String status;
    private boolean lateReturn;
    private double fineAmount;
    private long lateHours;
    private String message;

    public int getRentalId() {
        return rentalId;
    }

    public void setRentalId(int rentalId) {
        this.rentalId = rentalId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isLateReturn() {
        return lateReturn;
    }

    public void setLateReturn(boolean lateReturn) {
        this.lateReturn = lateReturn;
    }

    public double getFineAmount() {
        return fineAmount;
    }

    public void setFineAmount(double fineAmount) {
        this.fineAmount = fineAmount;
    }

    public long getLateHours() {
        return lateHours;
    }

    public void setLateHours(long lateHours) {
        this.lateHours = lateHours;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
