package com.increff.pos.pojo;

import javax.persistence.*;

@Entity
@Table(indexes = {@Index(columnList = "id")} , uniqueConstraints = @UniqueConstraint
        (columnNames = {"barcode"}))

public class ProductPojo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String barcode;
    private int brand_category_id;
    private String name;
    private Double mrp;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public int getBrand_category_id() {
        return brand_category_id;
    }

    public void setBrand_category_id(int brand_category_id) {
        this.brand_category_id = brand_category_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getMrp() {
        return mrp;
    }

    public void setMrp(Double mrp) {
        this.mrp = mrp;
    }
}
