package com.increff.employee.service;

import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.pojo.ProductPojo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static junit.framework.TestCase.assertEquals;

public class ProductServiceTest {

    @Autowired
    ProductService productService;

    @Test
    public void testProduct1(){
        ProductPojo b = new ProductPojo();

        b.setBarcode("871");
        assertEquals("871" , b.getBarcode());
    }

    @Test
    public void testProduct2(){
        ProductPojo b = new ProductPojo();

        b.setBarcode("871");
        assertEquals("871" , b.getBarcode());
    }

    @Test
    public void addTest() throws ApiException {
        ProductPojo b = new ProductPojo();
        b.setBarcode("9000");
        b.setName("5 star");
        b.setMrp(90.0);
        b = productService.add(b ,"hp", "envy" );

        assertEquals("5 star" , b.getName());
    }
}
