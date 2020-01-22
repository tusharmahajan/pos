package com.increff.employee.service;

import com.increff.employee.pojo.InventoryPojo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class InventoryServiceTest extends AbstractUnitTest {

    @Autowired
    InventoryService inventoryService;

    @Autowired
    ProductService productService;

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

    @Test(expected = ApiException.class)
    public void negativequantitytest() throws ApiException {
        InventoryPojo i = new InventoryPojo();

        i.setQuantity(100);
        i.setInventory_id(20);
        inventoryService.add(i);
        InventoryPojo i1  = new InventoryPojo();
        i1.setInventory_id(20);
        i1.setQuantity(-20);

        inventoryService.update(i.getInventory_id() , i1);
    }


    @Test
    public void updateTest() throws ApiException {
        InventoryPojo i = new InventoryPojo();
        i.setInventory_id(1);
        i.setQuantity(100);
        inventoryService.add(i);

        InventoryPojo i1 = inventoryService.getInventoryPojo(i.getInventory_id());
        i1.setQuantity(101);

        inventoryService.update(1 , i1);
    }

    @Test
    public void getAllTest() throws ApiException {

        List<InventoryPojo> ii = new ArrayList<>();

        for(int j  = 1;j < 3; j++) {
            InventoryPojo i = new InventoryPojo();
            i.setInventory_id(j);
            i.setQuantity(10*j);
            inventoryService.add(i);
            ii.add(i);
        }

        assertEquals(2 , inventoryService.getAll().size());
    }

    @Test
    public void addtest1() throws ApiException {
        InventoryPojo i = new InventoryPojo();
        i.setInventory_id(20);
        i.setQuantity(100);
        inventoryService.add(i);

        InventoryPojo i1 = new InventoryPojo();
        i1.setQuantity(100);
        i1.setInventory_id(20);
        inventoryService.add(i1);
    }

    @Test(expected = ApiException.class)
    public void negativetest1() throws ApiException {
        InventoryPojo i = new InventoryPojo();
        i.setInventory_id(20);
        i.setQuantity(-100);
        inventoryService.add(i);

    }

}
