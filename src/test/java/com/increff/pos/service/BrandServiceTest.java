package com.increff.pos.service;

import com.increff.pos.pojo.BrandPojo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class BrandServiceTest extends AbstractUnitTest {

    @Autowired
    private BrandService service;

    @Test
    public void testBrand(){
        BrandPojo b = new BrandPojo();

        b.setBrand("Cadbury");
        BrandService.normalize(b);
        assertEquals("cadbury" , b.getBrand());
    }

    @Test
    public void testCategory(){
        BrandPojo b = new BrandPojo();

        b.setCategory("CHOCOLATE");
        BrandService.normalize(b);
        assertEquals("chocolate" , b.getCategory());
    }

    @Test(expected = ApiException.class)
    public void addTest() throws ApiException {
        BrandPojo b = new BrandPojo();
        b.setCategory("chocolate");
        service.add(b);
    }

    @Test
    public void addTest1() throws ApiException {
        BrandPojo b = new BrandPojo();
        b.setBrand(" cadbury ");
        b.setCategory(" chocolate ");
        service.add(b);
    }

    @Test(expected = ApiException.class)
    public void addTest2() throws ApiException {
        BrandPojo b = new BrandPojo();
        b.setBrand(" cadbury ");
        service.add(b);
    }

    @Test
    public void testUpdate() throws ApiException {
        BrandPojo insert=new BrandPojo();
        insert.setBrand("abc");

        insert.setCategory("xyz");
        insert=service.add(insert);

        BrandPojo b = new BrandPojo();
        b.setBrand("cadbury");
        b.setCategory("chocolate");
        service.update(insert.getId() , b);
    }

    @Test
    public void testGet() throws ApiException {
        BrandPojo insert=new BrandPojo();

        insert.setBrand("abc");
        insert.setCategory("xyz");

        insert = service.add(insert);

        BrandPojo temp=service.getCheck(insert.getId());
        assertEquals(insert.getId(), temp.getId());
    }


    @Test
    public void testGetAll() throws ApiException {
        List<BrandPojo> brandPojos = new ArrayList<>();
        for(int i = 0;i<5;i++){
            BrandPojo brandPojo = new BrandPojo();
            brandPojo.setBrand("brand" + i);
            brandPojo.setCategory("category" + i);
            service.add(brandPojo);
            brandPojos.add(brandPojo);
        }
        assertEquals(5 , service.getAll().size());
    }

    @Test(expected = ApiException.class)
    public void testNullBrand() throws ApiException {
        BrandPojo b = new BrandPojo();
        b.setCategory("abc");
        b = service.add(b);
    }

    @Test(expected = ApiException.class)
    public void checkTest() throws ApiException {
        BrandPojo b = new BrandPojo();
        b.setBrand("xyz");
        b.setCategory("abc");
        service.check(b.getBrand() , b.getCategory());
    }

    @Test(expected = ApiException.class)
    public void checkTest1() throws ApiException {
        BrandPojo b = new BrandPojo();
        b.setBrand("xyz");
        b.setCategory("abc");
        service.add(b);

        service.getCheck(11);
    }
}
