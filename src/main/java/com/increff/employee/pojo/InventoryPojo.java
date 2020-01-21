package com.increff.employee.pojo;

import com.increff.employee.util.StringUtil;

import javax.persistence.Entity;

import javax.persistence.Id;

@Entity
public class InventoryPojo {

    @Id
    private int inventory_id;

    private int quantity;

    public int getInventory_id() {
        return inventory_id;
    }

    public void setInventory_id(int inventory_id) {
        this.inventory_id = inventory_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
