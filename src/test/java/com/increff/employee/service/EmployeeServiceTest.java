package com.increff.employee.service;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.increff.employee.pojo.BrandPojo;

public class EmployeeServiceTest extends AbstractUnitTest {

	@Autowired
	private BrandService service;

	@Test
	public void testAdd() throws ApiException {
		BrandPojo p = new BrandPojo();
		p.setBrand(" Romil Jain ");
		service.add(p);
	}

	@Test
	public void testNormalize() {
		BrandPojo p = new BrandPojo();
		p.setBrand(" Romil Jain ");
		BrandService.normalize(p);
		assertEquals("romil jain", p.getBrand());
	}

}
