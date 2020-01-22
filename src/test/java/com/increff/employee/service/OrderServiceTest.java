package com.increff.employee.service;

import com.increff.employee.dao.OrderDao;
import com.increff.employee.pojo.OrderPojo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class OrderServiceTest extends AbstractUnitTest {

    @Autowired
    OrderService orderService;

    @Test
    public void addtest(){
        OrderPojo o = new OrderPojo();
        o.setTime(new Date());
        o = orderService.add(o);

    }

    @Test
    public void gettest(){
        OrderPojo o = new OrderPojo();
        o.setTime(addtoDate(new Date() , 1));
        orderService.add(o);

        OrderPojo o1 = new OrderPojo();
        o1.setTime(addtoDate(new Date() , 1));
        orderService.add(o1);
        Date st = new Date();

        Date et = addtoDate(new Date() , 2);
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
        assertEquals(2 , orderService.getall().size());
    }

    private Date addtoDate(Date date, int i) {
        Calendar c = Calendar.getInstance();

        c.setTime(date);
        c.add(Calendar.DATE , i);
        return c.getTime();
    }

}
