package com.increff.pos.pojo;

public class SalesReportResult {
    private String category;
    private long quantity;
    private double revenue;

    public SalesReportResult(String category, long quantity, double revenue) {
        this.category = category;
        this.quantity = quantity;
        this.revenue = revenue;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getQuantity() {
        return (int)quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public double getRevenue() {
        return revenue;
    }

    public void setRevenue(double revenue) {
        this.revenue = revenue;
    }
}
