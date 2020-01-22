package com.increff.employee.service;

import com.increff.employee.pojo.OrderItemPojo;
import com.increff.employee.pojo.OrderPojo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

import static junit.framework.TestCase.assertEquals;

public class OrderItemServiceTest extends AbstractUnitTest{

    @Autowired
    OrderItemService orderItemService;

    @Autowired
    OrderService orderService;

    @Test
    public void addtest(){
        OrderItemPojo o = new OrderItemPojo();

        o.setProduct_id(1);
        o.setOrder_id(10);
        o.setSellingPrice(100.0);
        o.setQuantity(101);
        orderItemService.add(o);
    }

    @Test
    public void getAlltest(){
        OrderPojo o11 = new OrderPojo();
        o11.setTime(new Date());
        orderService.add(o11);

        OrderItemPojo o = new OrderItemPojo();

        o.setProduct_id(1);
        o.setOrder_id(o11.getOrderId());
        o.setSellingPrice(100.0);
        o.setQuantity(101);
        orderItemService.add(o);

        OrderItemPojo o1 = new OrderItemPojo();

        o1.setProduct_id(2);
        o1.setOrder_id(o11.getOrderId());
        o1.setSellingPrice(101.0);
        o1.setQuantity(102);
        orderItemService.add(o1);

        assertEquals(2 , orderItemService.getorder(o11.getOrderId()).size());
    }
}
