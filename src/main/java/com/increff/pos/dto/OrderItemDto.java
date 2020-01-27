package com.increff.pos.dto;

import com.increff.pos.dao.PdfUtility;
import com.increff.pos.model.OrderItemData;
import com.increff.pos.model.OrderItemForm;
import com.increff.pos.pojo.OrderItemPojo;
import com.increff.pos.pojo.OrderPojo;
import com.increff.pos.pojo.ProductPojo;
import com.increff.pos.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderItemDto {

    @Autowired
    private ProductService productService;
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private InventoryService inventoryService;

    private int currentOrderId = -1;

    @Transactional
    public void add(List<OrderItemForm> form) throws Exception{
        OrderPojo order = new OrderPojo();
        order.setTime(new Date());
        order = orderService.add(order);
        for (OrderItemForm entry : form) {
            orderItemService.add(convert(order, entry) , entry);
        }
        currentOrderId =order.getOrderId();
    }

    public OrderItemData getProduct(String barcode) throws ApiException {
        ProductPojo p = productService.getBarcodePojo(barcode);
        OrderItemData data = new OrderItemData();
        data.setBarcode(barcode);
        data.setMrp(p.getMrp());
        data.setQuantity((inventoryService.getInventoryPojo(p.getId())).getQuantity());
        data.setName(p.getName());
        return data;
    }

    public byte[] generateReport() throws Exception {
        if(currentOrderId==-1)
        {
            throw new ApiException("Please place the order first");
        }
        List<OrderItemPojo> pojoList=orderService.getByOrderId(currentOrderId);
        List<OrderItemData> itemData=new ArrayList<>();
        for(OrderItemPojo item:pojoList)
        {
            itemData.add(convertForInvoice(item));
        }
        return PdfUtility.createPdfForInvoice(itemData,currentOrderId);
    }

    private OrderItemData convertForInvoice(OrderItemPojo item) {
        OrderItemData orderItemsData=new OrderItemData();
        ProductPojo product=productService.get(item.getProductId());
        orderItemsData.setQuantity(item.getQuantity());
        orderItemsData.setBarcode(product.getBarcode());
        orderItemsData.setName(product.getName());
        orderItemsData.setMrp(product.getMrp());
        return orderItemsData;
    }

    private OrderItemPojo convert(OrderPojo order, OrderItemForm form) throws ApiException {
        OrderItemPojo p = new OrderItemPojo();
        ProductPojo product = productService.getBarcodePojo(form.getBarcode());

        p.setOrderId(order.getOrderId());
        p.setQuantity(form.getQuantity());
        p.setProductId(product.getId());
        p.setSellingPrice(product.getMrp());
        return p;
    }


}