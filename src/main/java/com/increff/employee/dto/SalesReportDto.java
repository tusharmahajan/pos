package com.increff.employee.dto;

import com.increff.employee.model.SalesReportData;
import com.increff.employee.model.SalesReportForm;
import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.pojo.OrderItemPojo;
import com.increff.employee.pojo.OrderPojo;
import com.increff.employee.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class SalesReportDto {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderItemService orderItemsService;

    @Autowired
    private ProductService productService;

    @Autowired
    private BrandService brandService;

    public List<SalesReportData> get(SalesReportForm form) throws ApiException {
        return convert(form);
    }

    private List<SalesReportData> convert(SalesReportForm form) throws ApiException {
        List<SalesReportData> reportDataList=new ArrayList<SalesReportData>();
        List<OrderPojo> orders = orderService.get(form.getStartdate(),form.getEndate());
        List<OrderItemPojo> orderItems = new ArrayList<OrderItemPojo>();
        for(OrderPojo order : orders) {
            List<OrderItemPojo> oip=orderItemsService.getorder(order.getOrderId());
            for(OrderItemPojo op : oip){
                orderItems.add(op);
            }
        }

        if(form.getBrand().isEmpty() && form.getCategory().isEmpty()){
            HashMap<String ,double[]> categoryMap=new HashMap<String,double[]>();
            for(OrderItemPojo orderItem : orderItems){
                BrandPojo brand=brandService.get((productService.get(orderItem.getProduct_id())).getBrand_category());
                double[] array = new double[2];
                if(categoryMap.containsKey(brand.getCategory())){
                    array=categoryMap.get(brand.getCategory());
                    array[0]+=orderItem.getQuantity();
                    array[1]+=orderItem.getQuantity()*orderItem.getSellingPrice();
                    categoryMap.put(brand.getCategory(),array);
                }
                else{
                    array[0]=orderItem.getQuantity();
                    array[1]=orderItem.getQuantity()*orderItem.getSellingPrice();
                    categoryMap.put(brand.getCategory(),array);
                }
            }
            for (String category : categoryMap.keySet())
            {
                SalesReportData salesReportData=new SalesReportData();
                salesReportData.setCategory(category);
                int quantity=(int) categoryMap.get(category)[0];
                salesReportData.setQuantity(quantity);
                salesReportData.setRevenue(categoryMap.get(category)[1]);
                reportDataList.add(salesReportData);
            }
        }
        else{
            if(form.getBrand().isEmpty()){
                SalesReportData salesReportData=new SalesReportData();
                salesReportData.setQuantity(0);
                salesReportData.setRevenue(0.0);
                salesReportData.setCategory(form.getCategory());
                for(OrderItemPojo orderItem : orderItems){
                    String category=(brandService.get(productService.get(orderItem.getProduct_id()).getBrand_category())).getCategory();
                    if(category.equals(salesReportData.getCategory())){
                        salesReportData.setQuantity(salesReportData.getQuantity()+orderItem.getQuantity());
                        salesReportData.setRevenue(salesReportData.getRevenue()+(orderItem.getQuantity()*orderItem.getSellingPrice()));
                    }
                }
                reportDataList.add(salesReportData);
            }
            else{
                if(form.getCategory().isEmpty()){
                    HashMap<String ,double[]> categoryMap=new HashMap<String,double[]>();
                    for(OrderItemPojo orderItem : orderItems){
                        BrandPojo brand=brandService.get((productService.get(orderItem.getProduct_id())).getBrand_category());
                        if(brand.getBrand().equals(form.getBrand())){
                            double[] array = new double[2];
                            if(categoryMap.containsKey(brand.getCategory())){
                                array=categoryMap.get(brand.getCategory());
                                array[0]+=orderItem.getQuantity();
                                array[1]+=orderItem.getQuantity()*orderItem.getSellingPrice();
                                categoryMap.put(brand.getCategory(),array);
                            }
                            else{
                                array[0]=orderItem.getQuantity();
                                array[1]=orderItem.getQuantity()*orderItem.getSellingPrice();
                                categoryMap.put(brand.getCategory(),array);
                            }
                        }
                    }
                    for (String category : categoryMap.keySet())
                    {
                        SalesReportData salesReportData=new SalesReportData();
                        salesReportData.setCategory(category);
                        int quantity=(int) categoryMap.get(category)[0];
                        salesReportData.setQuantity(quantity);
                        salesReportData.setRevenue(categoryMap.get(category)[1]);
                        reportDataList.add(salesReportData);
                    }
                }
                else{
                    SalesReportData salesReportData=new SalesReportData();
                    salesReportData.setQuantity(0);
                    salesReportData.setRevenue(0.0);
                    salesReportData.setCategory(form.getCategory());
                    for(OrderItemPojo orderItem : orderItems){
                        BrandPojo brand=brandService.get(productService.get(orderItem.getProduct_id()).getBrand_category());
                        if(brand.getCategory().equals(salesReportData.getCategory()) && brand.getBrand().equals(form.getBrand())){
                            salesReportData.setQuantity(salesReportData.getQuantity()+orderItem.getQuantity());
                            salesReportData.setRevenue(salesReportData.getRevenue()+(orderItem.getQuantity()*orderItem.getSellingPrice()));
                        }
                    }
                    reportDataList.add(salesReportData);
                }
            }
        }
        return reportDataList;
    }
}