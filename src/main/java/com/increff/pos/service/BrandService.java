package com.increff.pos.service;

import com.increff.pos.dao.BrandDao;
import com.increff.pos.pojo.BrandPojo;
import com.increff.pos.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BrandService {

    @Autowired
    private BrandDao dao;

    @Transactional(rollbackFor = ApiException.class)
    public BrandPojo add(BrandPojo p) throws ApiException {
        normalize(p);
        if (StringUtil.isEmpty(p.getBrand())) {
            throw new ApiException("name cannot be empty");
        }
        if (StringUtil.isEmpty(p.getCategory())) {
            throw new ApiException("Category cannot be empty");
        }
        return dao.insert(p);
    }

    @Transactional(readOnly = true)
    public BrandPojo get(int id){
        return dao.select(id);
    }

    @Transactional(readOnly = true)
    public List<BrandPojo> getAll() {
        return dao.selectAll();
    }

    @Transactional(rollbackFor = ApiException.class)
    public void update(int id, BrandPojo p) throws ApiException {
        normalize(p);
        BrandPojo ex = getCheck(id);
        ex.setBrand(p.getBrand());
        ex.setCategory(p.getCategory());
        dao.update(ex);
    }

    @Transactional(rollbackFor = ApiException.class)
    public BrandPojo getCheck(int id) throws ApiException {
        BrandPojo p = get(id);
        if (p == null) {
            throw new ApiException("Brand with given ID does not exit, id: " + id);
        }
        return p;
    }

    public BrandPojo check(String brand, String category) throws ApiException {
        BrandPojo p = dao.selectBrandCategory(brand, category);
        if (p == null) {
            throw new ApiException("Either brand or category doesn't exist");
        }
        return p;
    }

    protected static void normalize(BrandPojo p) {
        p.setBrand(StringUtil.toLowerCase(p.getBrand()));
        p.setCategory(StringUtil.toLowerCase(p.getCategory()));
    }

}
