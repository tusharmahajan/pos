package com.increff.pos.model;

public class SalesReportData extends SalesReportForm {

    private int quantity;
    private Double revenue;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


    public Double getRevenue() {
        return revenue;
    }

    public void setRevenue(Double revenue) {
        this.revenue = revenue;
    }
}
