package com.increff.employee.dto;

import com.increff.employee.dao.InventoryDao;
import com.increff.employee.model.InventoryReportData;
import com.increff.employee.pojo.InventoryPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.BrandService;
import com.increff.employee.service.InventoryService;
import com.increff.employee.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class InventoryReportDto2 {

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private ProductService productService;

    @Autowired
    private BrandService brandService;

    @Autowired
    private InventoryDao inventoryDao;


    public List<InventoryReportData> getAll() {
        List<InventoryReportData> inventoryReportData = new ArrayList<>();
        List<Object[]> o = inventoryDao.selectByBrandCat();
        for(Object[] i : o){//0-brand , 1-category , 2- qty
            InventoryReportData temp=new InventoryReportData();
            temp.setCategory(i[1].toString());
            temp.setBrand(i[0].toString());
            Number number=(Number)i[2];
            temp.setQuantity(number.intValue());
            inventoryReportData.add(temp);
        }
        return  inventoryReportData;
    }
}
