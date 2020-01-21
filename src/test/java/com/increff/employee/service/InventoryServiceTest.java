package com.increff.employee.service;

import com.increff.employee.pojo.InventoryPojo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static junit.framework.TestCase.assertEquals;

public class InventoryServiceTest extends AbstractUnitTest {

    @Autowired
    InventoryService inventoryService;

    @Test
    public void idtest() throws ApiException {
        InventoryPojo i = new InventoryPojo();

        i.setInventory_id(1);
        i.setQuantity(100);

        inventoryService.add(i);

        assertEquals(1 , i.getInventory_id());
    }

    @Test
    public void addtest() throws ApiException {
        InventoryPojo i = new InventoryPojo();

        i.setQuantity(100);

        i = inventoryService.add(i);

        assertEquals(100 , i.getQuantity());
    }

    @Test
    public void negativequanittytest() throws ApiException {
        InventoryPojo i = new InventoryPojo();

        i.setQuantity(100);
        i.setInventory_id(20);
        inventoryService.add(i);
        assertEquals(12 , i.getQuantity());
    }


//    @Test
//    public void noidexist() throws ApiException {
//        InventoryPojo i = new InventoryPojo();
//
//        i.setQuantity(100);
//
//        i = inventoryService.add(i);
//
//    }
}
