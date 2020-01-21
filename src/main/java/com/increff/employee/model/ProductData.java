package com.increff.employee.model;

public class ProductData extends ProductForm {

    private int product_id;
    private int brand_category;


    public int getBrand_category() {
        return brand_category;
    }

    public void setBrand_category(int brand_category) {
        this.brand_category = brand_category;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }
}
