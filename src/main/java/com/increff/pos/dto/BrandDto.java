package com.increff.pos.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.increff.pos.model.BrandData;
import com.increff.pos.model.BrandForm;
import com.increff.pos.pojo.BrandPojo;
import com.increff.pos.service.ApiException;
import com.increff.pos.service.BrandService;

@Service
public class BrandDto {

    @Autowired
    private BrandService service;

    public void add(BrandForm form) throws ApiException {
        BrandPojo p = convert(form);
        service.add(p);
    }


    public BrandData get(int id) throws ApiException {
        BrandPojo p = service.getCheck(id);
        return convert(p);
    }


    public List<BrandData> getAll() {
        List<BrandPojo> list = service.getAll();
        List<BrandData> list2 = new ArrayList<BrandData>();
        for (BrandPojo p : list) {
            list2.add(convert(p));
        }
        return list2;
    }

    public void update(int id, BrandForm f) throws ApiException {
        BrandPojo p = convert(f);
        service.update(id, p);
    }


    private static BrandData convert(BrandPojo p) {
        BrandData d = new BrandData();
        d.setCategory(p.getCategory());
        d.setBrand(p.getBrand());
        d.setId(p.getId());
        return d;
    }

    private static BrandPojo convert(BrandForm f) {
        BrandPojo p = new BrandPojo();
        p.setCategory(f.getCategory());
        p.setBrand(f.getBrand());
        return p;
    }
    // REPORT GENERATION

    public final String xmlFilePath = "./brands.xml";


}
