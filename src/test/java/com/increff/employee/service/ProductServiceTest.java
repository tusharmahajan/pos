package com.increff.employee.service;

import com.increff.employee.dao.BrandDao;
import com.increff.employee.dao.ProductDao;
import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.pojo.ProductPojo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static junit.framework.TestCase.assertEquals;

public class ProductServiceTest {

    @Autowired
    ProductService productService;
    @Autowired
    ProductDao productDao;

    @Autowired
    BrandService brandService;

    @Autowired
    BrandDao brandDao;

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

//    @Test
//    public void addTest() throws ApiException {
//        ProductPojo b = new ProductPojo();
//        b.setBarcode("9000");
//        b.setName("5 star");
//        b.setMrp(90.0);
//        BrandPojo p = new BrandPojo();
//        p.setBrand("hp");
//        p.setCategory("envy");
//        p.setId(1);
//        p = brandDao.insert(p);
//        b.setBrand_category(123);
//        b.setProduct_id(456);
//        productDao.insert(b);
//
//    }
}
