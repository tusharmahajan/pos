package com.increff.pos.pojo;

// NON-ENTITY POJO
public class ProductDisplayPojo {

    private int id;
    private String name;
    private String barcode;
    private String brand;
    private int brand_category_id;
    private Double mrp;
    private String category;

    public ProductDisplayPojo(int id, String name, String barcode, String brand,  String category ,int brand_category_id, Double mrp) {
        this.id = id;
        this.name = name;
        this.barcode = barcode;
        this.brand = brand;
        this.brand_category_id = brand_category_id;
        this.mrp = mrp;
        this.category = category;
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

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getBrand_category_id() {
        return brand_category_id;
    }

    public void setBrand_category_id(int brand_category_id) {
        this.brand_category_id = brand_category_id;
    }

    public Double getMrp() {
        return mrp;
    }

    public void setMrp(Double mrp) {
        this.mrp = mrp;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
