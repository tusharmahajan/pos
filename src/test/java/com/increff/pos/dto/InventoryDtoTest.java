package com.increff.pos.dto;

import com.increff.pos.dao.BrandDao;
import com.increff.pos.dao.InventoryDao;
import com.increff.pos.dao.ProductDao;
import com.increff.pos.model.InventoryForm;
import com.increff.pos.pojo.BrandPojo;
import com.increff.pos.pojo.InventoryPojo;
import com.increff.pos.pojo.ProductPojo;
import com.increff.pos.service.ApiException;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static junit.framework.TestCase.assertEquals;

public class InventoryDtoTest extends AbstractUnitTest {

    @Autowired
    InventoryDto inventoryDto;

    @Autowired
    private BrandDao brandDao;

    @Autowired
    private ProductDao productDao;

    private InventoryForm form;
    private InventoryPojo i;

    @Autowired
    private InventoryDao inventoryDao;

    private BrandPojo brandPojo;
    private ProductPojo productPojo;
    @Before
    public void init(){
        form = new InventoryForm();
        form.setBarcode("800");
        form.setQuantity(200);

        brandPojo = new BrandPojo();
        brandPojo.setBrand("hp");
        brandPojo.setCategory("envy");
        brandDao.insert(brandPojo);


        productPojo = new ProductPojo();
        productPojo.setBarcode("800");
        productPojo.setMrp(90.0);
        productPojo.setName("big");
        productPojo.setBrand_category_id(brandPojo.getId());
        productDao.insert(productPojo);


        i = new InventoryPojo();
        i.setQuantity(200);
//        inventoryDao.insert(i);
    }

    @Test
    public void addTest() throws ApiException {

        inventoryDto.add(form);
    }

    @Test
    public void getAllTest() throws ApiException {

        i.setInventoryId(productPojo.getId());

        i = inventoryDao.insert(i);
        ProductPojo p1 = new ProductPojo();
        p1.setBarcode("801");
        p1.setMrp(99.9);
        p1.setName("big1");
        p1.setBrand_category_id(brandPojo.getId());
        productDao.insert(p1);

        InventoryPojo i1 = new InventoryPojo();
        i1.setQuantity(99);

        i1.setInventoryId(p1.getId());
        i1 = inventoryDao.insert(i1);

        assertEquals(2 , inventoryDto.getAll().size());
    }

    @Test
    public void getTest() throws ApiException {

        i.setInventoryId(productPojo.getId());
        i = inventoryDao.insert(i);
        assertEquals(i.getInventoryId(),inventoryDto.get(i.getInventoryId()).getInventoryId());
    }

//    @Test
//    public void updateTest() throws ApiException {
//        i.setInventory_id(productPojo.getProduct_id());
//        i = inventoryDao.insert(i);
//
//        ProductPojo p1 = new ProductPojo();
//        p1.setBarcode("801");
//        p1.setMrp(99.9);
//        p1.setName("big1");
//        p1.setBrand_category(brandPojo.getId());
//        productDao.insert(p1);
//
//        InventoryPojo i1 = new InventoryPojo();
//        i1.setQuantity(99);
//
//        i1.setInventory_id(p1.getProduct_id());
//
//        inventoryDto.update(i.getInventory_id() , );
//    }
}
