package com.increff.pos.dto;

import com.increff.pos.dao.BrandDao;
import com.increff.pos.model.BrandForm;
import com.increff.pos.pojo.BrandPojo;
import com.increff.pos.service.ApiException;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;

public class BrandDtoTest extends AbstractUnitTest {

    @Autowired
    private BrandDto brandDto;

    @Autowired
    private BrandDao brandDao;

    private BrandForm form;
    private BrandPojo b;


    @Before
    public void init(){
        form = new BrandForm();
        form.setCategory("envy");
        form.setBrand("hp");

        b = new BrandPojo();
        b.setCategory("envy");
        b.setBrand("hp");
    }

    @Test
    public void addtest() throws ApiException {

        brandDto.add(form);
    }

    @Test
    public void getalltest() throws ApiException {

        brandDto.add(form);
//        addtest();
        assertEquals(1, brandDto.getAll().size());
    }


    @Test
    public void gettest() throws ApiException {

        brandDao.insert(b);
//        addtest();
        assertEquals(b.getId(), brandDto.get(b.getId()).getId());
    }


    @Test
    public void updatetest() throws ApiException {

        brandDao.insert(b);

        BrandForm b1 = new BrandForm();

        b1.setBrand("hp1");
        b1.setCategory("envy1");

        brandDto.update(b.getId() , b1);
    }
}
