package com.increff.pos.service;

import com.increff.pos.pojo.OrderItemPojo;
import com.increff.pos.pojo.OrderPojo;
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
    public void addTest(){
        OrderItemPojo o = new OrderItemPojo();

        o.setProductId(1);
        o.setOrderId(10);
        o.setSellingPrice(100.0);
        o.setQuantity(101);
//        orderItemService.add(o);
    }

    @Test
    public void getAllTest(){
        OrderPojo o11 = new OrderPojo();
        o11.setTime(new Date());
        orderService.add(o11);

        OrderItemPojo o = new OrderItemPojo();

        o.setProductId(1);
        o.setOrderId(o11.getOrderId());
        o.setSellingPrice(100.0);
        o.setQuantity(101);
//        orderItemService.add();

        OrderItemPojo o1 = new OrderItemPojo();

        o1.setProductId(2);
        o1.setOrderId(o11.getOrderId());
        o1.setSellingPrice(101.0);
        o1.setQuantity(102);
//        orderItemService.add(o1);

        assertEquals(2 , orderItemService.getOrder(o11.getOrderId()).size());
    }
}
