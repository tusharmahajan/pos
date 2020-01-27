package com.increff.pos.pojo;

public class InventoryReportPojo {

    private String brand;
    private String category;
    private long quantity;

    public InventoryReportPojo(String brand, String category, long quantity) {
        this.brand = brand;
        this.category = category;
        this.quantity = quantity;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }
}