package com.increff.employee.model;

import java.time.LocalDateTime;
import java.util.Date;

public class SalesReportForm {
//    @Override
//    public String toString() {
//        return "SalesReportForm{" +
//                "startdate=" + startdate +
//                ", endate=" + endate +
//                ", brand='" + brand + '\'' +
//                ", category='" + category + '\'' +
//                '}';
//    }

    private Date startdate;
    private Date endate;

    private String brand;
    private String category;

    public Date getStartdate() {
        return startdate;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    public Date getEndate() {
        return endate;
    }

    public void setEndate(Date endate) {
        this.endate = endate;
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
}