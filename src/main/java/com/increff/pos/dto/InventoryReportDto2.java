package com.increff.pos.dto;

import com.increff.pos.dao.InventoryDao;
import com.increff.pos.model.InventoryReportData;
import com.increff.pos.pojo.InventoryReportPojo;
import com.increff.pos.service.BrandService;
import com.increff.pos.service.InventoryService;
import com.increff.pos.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        List<InventoryReportPojo> o = inventoryDao.selectByBrandCat();
        for(InventoryReportPojo i : o){//0-brand , 1-category , 2- qty
            InventoryReportData temp=new InventoryReportData();
            temp.setCategory(i.getCategory());
            temp.setBrand(i.getBrand());
            temp.setQuantity((int) i.getQuantity());
            inventoryReportData.add(temp);
        }
        return  inventoryReportData;
    }
}
