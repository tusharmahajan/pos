package com.increff.employee.dto;

import com.increff.employee.model.InventoryData;
import com.increff.employee.model.InventoryForm;
import com.increff.employee.pojo.InventoryPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.InventoryService;
import com.increff.employee.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InventoryDto {

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private ProductService productService;


    public void add(InventoryForm form) throws ApiException {
        InventoryPojo p = convert(form);
        inventoryService.add(p);
    }


    public InventoryData get(int id) throws ApiException {
        InventoryPojo p = inventoryService.getInventoryPojo(id);
        return convert(p);
    }

    public List<InventoryData> getAll() throws ApiException {
        List<InventoryPojo> list = inventoryService.getAll();
        List<InventoryData> list2 = new ArrayList<InventoryData>();
        for (InventoryPojo p : list) {
            list2.add(convert(p));
        }
        return list2;
    }

    public void update(int id, InventoryForm f) throws ApiException {
        InventoryPojo p = convert(f);
        inventoryService.update(id, p);
    }

    public String get_barcode(int id) throws ApiException {
        return productService.get(id).getBarcode();
    }

    public String get_productname(int id) throws ApiException {

        return productService.get(id).getName();
    }

    public int get_product_id(String barcode) throws ApiException {
        return productService.get_product_id(barcode);
    }

    private InventoryData convert(InventoryPojo p) throws ApiException {
        InventoryData d = new InventoryData();
        d.setQuantity(p.getQuantity());
        d.setInventory_id(p.getInventory_id());
        d.setBarcode(get_barcode(p.getInventory_id()));
        d.setProduct_name(get_productname(p.getInventory_id()));
        return d;
    }

    private InventoryPojo convert(InventoryForm f) throws ApiException {
        InventoryPojo p = new InventoryPojo();
        p.setQuantity(f.getQuantity());

        p.setInventory_id(get_product_id(f.getBarcode()));
        return p;
    }
}
