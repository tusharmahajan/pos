package com.increff.pos.service;

import com.increff.pos.dao.InventoryDao;
import com.increff.pos.dto.InventoryDto;
import com.increff.pos.pojo.InventoryDisplayPojo;
import com.increff.pos.pojo.InventoryPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class InventoryService {

    @Autowired
    private InventoryDao inventory_dao;

    @Autowired
    private InventoryDto inventoryDto;

    @Transactional(rollbackFor = ApiException.class)
    public InventoryPojo add(InventoryPojo i) throws ApiException {

        InventoryPojo exist = inventory_dao.select(i.getInventoryId());

        if (i.getQuantity() < 0) {
            throw new ApiException("Fill quantity value or cant be negative");
        }
        if (exist == null && i.getQuantity() == i.getQuantity()) return inventory_dao.insert(i);
        else {
            int new_quantity = i.getQuantity() + exist.getQuantity();
            exist.setQuantity(new_quantity);
        }
        return null;
    }

    @Transactional(rollbackFor = ApiException.class)
    public void update(int id, InventoryPojo p) throws ApiException {

        InventoryPojo ip = getInventoryPojo(id);
        // to do weather to update barcode
        if (p.getQuantity() >= 0) {
            ip.setInventoryId(p.getInventoryId());
            ip.setQuantity(p.getQuantity());
            inventory_dao.update(ip);
            return;
        }
            throw new ApiException("Quantity doesn't exist");
    }
    @Transactional(readOnly = true)
    public InventoryPojo getInventoryPojo(int id){
        return inventory_dao.select(id);
    }

    @Transactional(readOnly = true)
    public List<InventoryDisplayPojo> getAll() {
        return inventory_dao.selectAll();
    }

}
