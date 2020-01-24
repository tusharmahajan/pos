package com.increff.employee.dto;

import com.increff.employee.dao.OrderItemDao;
import com.increff.employee.model.SalesReportData;
import com.increff.employee.model.SalesReportForm;
import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.pojo.OrderItemPojo;
import com.increff.employee.pojo.OrderPojo;
import com.increff.employee.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class SalesReportDto2 {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderItemService orderItemsService;

    @Autowired
    private ProductService productService;

    @Autowired
    private BrandService brandService;

    @Autowired
    private OrderItemDao orderItemDao;

    public List<SalesReportData> get(SalesReportForm form) throws ApiException {
//        System.out.println(form.getBrand()+" " + form.getCategory() + " "+form.getStartdate());
        List<SalesReportData> salesReportData = new ArrayList<>();

        if (form.getEndate() == null) {
            form.setEndate(new Date());
        }
        if (form.getStartdate().compareTo(form.getEndate()) > 0)
            throw new ApiException("Start Date cannot be more than EndDate");

        List<Object[]> o = orderItemDao.selectByOrderIdDate(form.getBrand(), form.getCategory(),
                form.getStartdate(), form.getEndate());
        for (Object[] i : o) {
            SalesReportData temp = new SalesReportData();
            temp.setCategory(i[0].toString());
            Number num = (Number) i[1];
            temp.setQuantity(num.intValue());
            temp.setRevenue((Double) i[2]);
            salesReportData.add(temp);
        }
        return salesReportData;
    }
}