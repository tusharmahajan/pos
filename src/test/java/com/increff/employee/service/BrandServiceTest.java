package com.increff.employee.service;

import com.increff.employee.pojo.BrandPojo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;

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

    @Test
    public void addTest() throws ApiException {
        BrandPojo b = new BrandPojo();
        b.setCategory(" chocolate ");
        service.add(b);
    }

    @Test
    public void addTest1() throws ApiException {
        BrandPojo b = new BrandPojo();
        b.setBrand(" cadbury ");
        b.setCategory(" chocolate ");
        service.add(b);
    }

    @Test
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

        BrandPojo temp=service.get(insert.getId());
        assertEquals("cadbury", temp.getBrand());
    }

    @Test
    public void testGet() throws ApiException {
        BrandPojo insert=new BrandPojo();

        insert.setBrand("abc");
        insert.setCategory("xyz");

        insert = service.add(insert);

        BrandPojo temp=service.get(insert.getId());
        assertEquals(insert.getId(), temp.getId());
    }

}
