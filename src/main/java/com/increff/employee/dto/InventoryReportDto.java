package com.increff.employee.dto;

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
public class InventoryReportDto {

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private ProductService productService;

    @Autowired
    private BrandService brandService;


    public List<InventoryReportData> getAll() throws ApiException {
        List<InventoryReportData> inventoryReportData = new ArrayList<>();

        List<InventoryPojo> inventoryPojos = inventoryService.getAll();
        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
        for (InventoryPojo i : inventoryPojos) {
            if (map.containsKey(productService.getCheck(i.getInventoryId()).getBrand_category())) {
                map.put(productService.getCheck(i.getInventoryId()).getBrand_category(), i.getQuantity() + i.getQuantity());
            } else {
                map.put(productService.getCheck(i.getInventoryId()).getBrand_category(), i.getQuantity());
            }
        }
        for (Integer i : map.keySet()) {
            InventoryReportData inventoryReportData1 = new InventoryReportData();
            inventoryReportData1.setQuantity(map.get(i));
            inventoryReportData1.setBrand(brandService.getCheck(i).getBrand());
            inventoryReportData1.setCategory(brandService.getCheck(i).getCategory());
            inventoryReportData.add(inventoryReportData1);
        }
        return inventoryReportData;
    }
}
