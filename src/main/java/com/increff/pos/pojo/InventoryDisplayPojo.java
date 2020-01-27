package com.increff.pos.pojo;

//NON ENTITY POJO TO REDUCE DB CALLS

public class InventoryDisplayPojo {

    private int id;
    private String name;
    private String barcode;
    private int quantity;

    public InventoryDisplayPojo(int id, String name, String barcode, int quantity) {
        this.id = id;
        this.name = name;
        this.barcode = barcode;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
