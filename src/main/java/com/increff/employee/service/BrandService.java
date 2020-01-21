package com.increff.employee.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.increff.employee.dao.BrandDao;
import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.util.StringUtil;

@Service
public class BrandService {

	@Autowired
	private BrandDao dao;

	@Transactional(rollbackOn = ApiException.class)
	public BrandPojo add(BrandPojo p) throws ApiException {
		normalize(p);
		if(StringUtil.isEmpty(p.getBrand())) {
			throw new ApiException("name cannot be empty");
		}
		if(StringUtil.isEmpty(p.getCategory())) {
			throw new ApiException("Category cannot be empty");
		}

		return dao.insert(p);
	}


	@Transactional(rollbackOn = ApiException.class)
	public BrandPojo get(int id) throws ApiException {
		return getCheck(id);
	}

	@Transactional
	public List<BrandPojo> getAll() {
		return dao.selectAll();
	}

	@Transactional(rollbackOn  = ApiException.class)
	public void update(int id, BrandPojo p) throws ApiException {
		normalize(p);
		BrandPojo ex = getCheck(id);
		ex.setBrand(p.getBrand());
		ex.setCategory(p.getCategory());
		dao.update(ex);
	}

	@Transactional
	public BrandPojo getCheck(int id) throws ApiException {
		BrandPojo p = dao.select(id);
		if (p == null) {
			throw new ApiException("Brand with given ID does not exit, id: " + id);
		}
		return p;
	}

	protected static void normalize(BrandPojo p) {
		p.setBrand(StringUtil.toLowerCase(p.getBrand()));
		p.setCategory(StringUtil.toLowerCase(p.getCategory()));
	}

	public BrandPojo check(String brand, String category) throws ApiException {
//	System.out.println(brand+ " "+ category);

		BrandPojo p = dao.selectBrandCategory(brand ,category);
//		System.out.println(brand+","+category);
		if(p==null){
			throw new ApiException("Either brand or category doesn't exist");
		}
		return p;
	}
}
