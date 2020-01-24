package com.increff.employee.service;

import com.increff.employee.dao.OrderDao;
import com.increff.employee.pojo.OrderPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderDao orderDao;

    @Transactional
    public OrderPojo add(OrderPojo order){
        return orderDao.insert(order);
    }

    @Transactional(readOnly = true)
    public List<OrderPojo> get(Date startDate , Date endDate){
        return orderDao.select(startDate , endDate);
    }

    @Transactional(readOnly = true)
    public List<OrderPojo> getAll(){
        return orderDao.selectAll();
    }
}