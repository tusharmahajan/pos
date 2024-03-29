package com.increff.pos.dto;

import com.increff.pos.model.SalesReportData;
import com.increff.pos.model.SalesReportForm;
import com.increff.pos.pojo.BrandPojo;
import com.increff.pos.pojo.OrderItemPojo;
import com.increff.pos.pojo.OrderPojo;
import com.increff.pos.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
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
//        System.out.println(form.getBrand().isEmpty()+" " + form.getCategory().isEmpty() + " "+form.getStartdate());
        if(form.getEndate() == null){
            form.setEndate(new Date());
        }
        if(form.getStartdate().compareTo(form.getEndate())>0) throw new ApiException("Start Date cannot be more than EndDate");
        return convert(form);
    }

    private List<SalesReportData> convert(SalesReportForm form) throws ApiException {
        List<SalesReportData> reportDataList=new ArrayList<SalesReportData>();
        List<OrderPojo> orders = orderService.get(form.getStartdate(),form.getEndate());
        List<OrderItemPojo> orderItems = new ArrayList<OrderItemPojo>();
//        System.out.println(orders);
//        System.out.println("hey");
        for(OrderPojo order : orders) {
            List<OrderItemPojo> oip=orderItemsService.getOrder(order.getOrderId());
            for(OrderItemPojo op : oip){
                orderItems.add(op);
            }
//            System.out.println(orderItems);
        }

        if(form.getBrand().isEmpty() && form.getCategory().isEmpty()){
            HashMap<String ,double[]> categoryMap=new HashMap<String,double[]>();
            for(OrderItemPojo orderItem : orderItems){
                BrandPojo brand=brandService.getCheck((productService.getCheck(orderItem.getProductId())).getBrand_category_id());
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
                System.out.println(categoryMap.values());
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
                    String category=(brandService.getCheck(productService.getCheck(orderItem.getProductId()).getBrand_category_id())).getCategory();
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
                        BrandPojo brand=brandService.getCheck((productService.getCheck(orderItem.getProductId())).getBrand_category_id());
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
                        BrandPojo brand=brandService.getCheck(productService.getCheck(orderItem.getProductId()).getBrand_category_id());
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