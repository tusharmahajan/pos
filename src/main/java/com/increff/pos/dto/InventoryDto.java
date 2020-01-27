package com.increff.pos.dto;

import com.increff.pos.model.InventoryData;
import com.increff.pos.model.InventoryForm;
import com.increff.pos.pojo.InventoryDisplayPojo;
import com.increff.pos.pojo.InventoryPojo;
import com.increff.pos.service.ApiException;
import com.increff.pos.service.InventoryService;
import com.increff.pos.service.ProductService;
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
        List<InventoryDisplayPojo> list = inventoryService.getAll();
        List<InventoryData> list2 = new ArrayList<InventoryData>();
        for (InventoryDisplayPojo p : list) {
            list2.add(convertForGetAll(p));
        }
        return list2;
    }

    public void update(int id, InventoryForm f) throws ApiException {
        InventoryPojo p = convert(f);
        inventoryService.update(id, p);
    }

    public String getBarcode(int id) throws ApiException {
        return productService.getCheck(id).getBarcode();
    }

    public String getProductName(int id) throws ApiException {

        return productService.getCheck(id).getName();
    }

    public int getProductId(String barcode) throws ApiException {
        return productService.getProductId(barcode);
    }

    private InventoryData convertForGetAll(InventoryDisplayPojo p) throws ApiException {
        InventoryData d = new InventoryData();
        d.setQuantity(p.getQuantity());
        d.setInventoryId(p.getId());
        d.setBarcode(p.getBarcode());
        d.setProduct_name(p.getName());
        return d;

    }

    private InventoryData convert(InventoryPojo p) throws ApiException {
        InventoryData d = new InventoryData();
        d.setQuantity(p.getQuantity());
        d.setInventoryId(p.getInventoryId());
        d.setBarcode(getBarcode(p.getInventoryId()));
        d.setProduct_name(getProductName(p.getInventoryId()));
        return d;
    }

    private InventoryPojo convert(InventoryForm f) throws ApiException {
        InventoryPojo p = new InventoryPojo();
        p.setQuantity(f.getQuantity());

        p.setInventoryId(getProductId(f.getBarcode()));
        return p;
    }
}
