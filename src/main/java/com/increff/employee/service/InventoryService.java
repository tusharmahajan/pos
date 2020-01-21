package com.increff.employee.service;

import com.increff.employee.dao.InventoryDao;
import com.increff.employee.dto.InventoryDto;
import com.increff.employee.pojo.InventoryPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class InventoryService {

    @Autowired
    private InventoryDao inventory_dao;

    @Autowired
    private InventoryDto inventoryDto;

    @Transactional
    public void add(InventoryPojo i) throws ApiException {

        InventoryPojo exist = inventory_dao.select(i.getInventory_id());

        if (i.getQuantity() < 0) {
            throw new ApiException("Fill quantity value or cant be negative");
        }
        if (exist == null && i.getQuantity() == i.getQuantity()) inventory_dao.insert(i);
        else {
            int new_quantity = i.getQuantity() + exist.getQuantity();
            exist.setQuantity(new_quantity);
//            update(i.getInventory_id() , exist);
        }
    }

    @Transactional(rollbackOn = ApiException.class)
    public void update(int id, InventoryPojo p) throws ApiException {

        InventoryPojo ip = getInventoryPojo(id);
        // to do weather to update barcode
        if (p.getQuantity() >= 0) {
            ip.setInventory_id(p.getInventory_id());
            ip.setQuantity(p.getQuantity());
            inventory_dao.update(ip);
        } else {
            throw new ApiException("Quantity doesn't exist");
        }
    }

    @Transactional(rollbackOn = ApiException.class)
    public InventoryPojo getInventoryPojo(int id) throws ApiException {
        return inventory_dao.select(id);
    }

    @Transactional
    public List<InventoryPojo> getAll() {
        return inventory_dao.selectAll();
    }

    public String get_barcode(int id) throws ApiException {
        return inventoryDto.get_barcode(id);
    }

    public String get_productname(int id) throws ApiException {

        return inventoryDto.get_productname(id);
    }

    public int get_product_id(String barcode) throws ApiException {
        return inventoryDto.get_product_id(barcode);
    }
}
