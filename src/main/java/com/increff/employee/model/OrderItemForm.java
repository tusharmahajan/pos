package com.increff.employee.model;

public class OrderItemForm {

    private String barcode;

    private int quantity;

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

//    @Override
//    public String toString() {
//        return "OrderItemForm{" +
//                "barcode='" + barcode + '\'' +
//                ", quantity=" + quantity +
//                '}';
//    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
