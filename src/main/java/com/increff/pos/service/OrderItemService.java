package com.increff.pos.service;

import com.increff.pos.dao.OrderItemDao;
import com.increff.pos.model.OrderItemForm;
import com.increff.pos.pojo.InventoryPojo;
import com.increff.pos.pojo.OrderItemPojo;
import com.increff.pos.pojo.ProductPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderItemService {

    @Autowired
    private OrderItemDao orderItemDao;

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private  ProductService productService;


    @Transactional
    public void add(OrderItemPojo orderItem , OrderItemForm form) throws ApiException {
        ProductPojo product = productService.getBarcodePojo(form.getBarcode());

        InventoryPojo inventory = inventoryService.getInventoryPojo(product.getId());
        inventory.setQuantity(inventory.getQuantity() - form.getQuantity());
        inventoryService.update(product.getId(), inventory);

        orderItemDao.insert(orderItem);
    }


    @Transactional(readOnly = true)
    public List<OrderItemPojo> getOrder(int id){
        return orderItemDao.selectAll(id);
    }

}