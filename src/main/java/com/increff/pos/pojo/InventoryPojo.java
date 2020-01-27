package com.increff.pos.pojo;

import javax.persistence.Entity;

import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

@Entity
@Table(indexes = @Index(columnList = "inventoryId") )

public class InventoryPojo {

    @Id
    private int inventoryId;

    private int quantity;

    public int getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(int inventoryId) {
        this.inventoryId = inventoryId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
