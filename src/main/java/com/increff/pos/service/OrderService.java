package com.increff.pos.service;

import com.increff.pos.dao.OrderDao;
import com.increff.pos.dao.OrderItemDao;
import com.increff.pos.pojo.OrderItemPojo;
import com.increff.pos.pojo.OrderPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private OrderItemDao orderItemDao;


    @Transactional
    public OrderPojo add(OrderPojo order){
        return orderDao.insert(order);
    }

    @Transactional(readOnly = true)
    public List<OrderPojo> get(Date startDate , Date endDate){
        return orderDao.select(startDate , endDate);
    }

    @Transactional(readOnly = true)
    public List<OrderItemPojo> getByOrderId(int id){
        return orderItemDao.selectByOrderId(id);
    }

    @Transactional(readOnly = true)
    public List<OrderPojo> getAll(){
        return orderDao.selectAll();
    }
}