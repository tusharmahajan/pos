package com.increff.employee.service;

import com.increff.employee.pojo.OrderPojo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Calendar;
import java.util.Date;

import static junit.framework.TestCase.assertEquals;

public class OrderServiceTest extends AbstractUnitTest {

    @Autowired
    OrderService orderService;

    @Test
    public void addTest(){
        OrderPojo o = new OrderPojo();
        o.setTime(new Date());
        o = orderService.add(o);

    }

    @Test
    public void gettest(){
        OrderPojo o = new OrderPojo();
        o.setTime(addToDate(new Date() , 1));
        orderService.add(o);

        OrderPojo o1 = new OrderPojo();
        o1.setTime(addToDate(new Date() , 1));
        orderService.add(o1);
        Date st = new Date();

        Date et = addToDate(new Date() , 2);
        assertEquals(2 , orderService.get(st , et).size());
    }

    @Test
    public void testGetAll(){
        OrderPojo o = new OrderPojo();
        o.setTime(new Date());
        orderService.add(o);

        OrderPojo o1 = new OrderPojo();
        o.setTime(new Date());
        orderService.add(o1);
        assertEquals(2 , orderService.getAll().size());
    }

    private Date addToDate(Date date, int i) {
        Calendar c = Calendar.getInstance();

        c.setTime(date);
        c.add(Calendar.DATE , i);
        return c.getTime();
    }

}
