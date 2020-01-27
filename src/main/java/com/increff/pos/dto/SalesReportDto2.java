package com.increff.pos.dto;

import com.increff.pos.dao.OrderItemDao;
import com.increff.pos.model.SalesReportData;
import com.increff.pos.model.SalesReportForm;
import com.increff.pos.pojo.SalesReportResult;
import com.increff.pos.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
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
        List<SalesReportData> salesReportData = new ArrayList<>();

        if (form.getEndate() == null) {
            form.setEndate(new Date());
        }
        long diff = (form.getEndate().getTime()-form.getStartdate().getTime())/(1000 * 60 * 60 * 24);

        if (form.getStartdate().compareTo(form.getEndate()) > 0)
            throw new ApiException("Start Date cannot be more than EndDate");
        if(diff > 30){
            throw new ApiException("Data not available");
        }

        List<SalesReportResult> o = orderItemDao.selectByOrderIdDate(form.getBrand(), form.getCategory(),
                form.getStartdate(), form.getEndate());
        for (SalesReportResult i : o) {
            SalesReportData temp = new SalesReportData();
            temp.setCategory(i.getCategory());
            temp.setQuantity(i.getQuantity());
            temp.setRevenue(i.getRevenue());
            salesReportData.add(temp);
        }
        return salesReportData;
    }
}