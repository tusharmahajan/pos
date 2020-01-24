package com.increff.employee.service;

import com.increff.employee.dao.OrderItemDao;
import com.increff.employee.pojo.OrderItemPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderItemService {


    @Autowired
    private OrderItemDao orderItemDao;

    @Transactional
    public void add(OrderItemPojo orderItem){
        orderItemDao.insert(orderItem);
    }

    @Transactional(readOnly = true)
    public List<OrderItemPojo> getOrder(int id){
        return orderItemDao.selectAll(id);
    }

}