package com.increff.employee.pojo;

import javax.persistence.*;

@Entity
@Table(indexes = @Index(columnList = "order_id , quantity") )
public class OrderItemPojo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderitem_id;

    private int order_id;

    private int product_id;

    private int quantity;

    private Double sellingPrice;

//    public int getOrderitem_id() {
//        return orderitem_id;
//    }
//
//    public void setOrderitem_id(int orderitem_id) {
//        this.orderitem_id = orderitem_id;
//    }
//
    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(Double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }
}
