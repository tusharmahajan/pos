package com.increff.employee.dto;

import com.increff.employee.dao.BrandDao;
import com.increff.employee.dao.ProductDao;
import com.increff.employee.model.BrandForm;
import com.increff.employee.model.ProductForm;
import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.service.ApiException;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static junit.framework.TestCase.assertEquals;

public class ProductDtoTest  extends AbstractUnitTest{

    @Autowired
    ProductDto productDto;

    @Autowired
    private BrandDao brandDao;

    @Autowired
    private ProductDao productDao;

    private ProductForm form ;
    private ProductPojo p;
    private BrandPojo b;

    @Before
    public void init(){
        form = new ProductForm();
        form.setBarcode("800");
        form.setBrand("hp");
        form.setCategory("envy");
        form.setMrp(90.0);
        form.setName("big");

        p = new ProductPojo();
        p.setBarcode("800");
        p.setMrp(90.0);
        p.setName("big");

        b = new BrandPojo();
        b.setCategory("envy");
        b.setBrand("hp");
        brandDao.insert(b);

        p.setBrand_category(b.getId());
    }

    @Test
    public void addTest() throws ApiException {
        productDto.add(form);
    }

    @Test
    public void updateTest() throws ApiException {
      ProductForm f = new ProductForm();
        f.setBarcode("801");
        f.setBrand("hp");
        f.setCategory("envy");
        f.setMrp(110.0);
        f.setName("big1");
        productDao.insert(p);
        productDto.update(p.getProduct_id() , f);
    }

    @Test
    public void getAllTest() throws ApiException {
        ProductPojo p1 = new ProductPojo();
        p1.setBarcode("802");
        p1.setMrp(190.0);
        p1.setName("big1");
        productDao.insert(p);
        p1.setBrand_category(b.getId());
        productDao.insert(p1);
        assertEquals(2 , productDto.getAll().size());
    }


    @Test
    public void getTest() throws ApiException {
        productDao.insert(p);

        assertEquals(p.getProduct_id() , productDto.get(p.getProduct_id()).getProduct_id());
    }

}
