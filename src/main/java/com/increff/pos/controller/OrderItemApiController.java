package com.increff.pos.controller;


import com.increff.pos.dto.OrderItemDto;
import com.increff.pos.model.OrderItemData;
import com.increff.pos.model.OrderItemForm;
import com.increff.pos.service.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Api
@RestController
public class OrderItemApiController {

    @Autowired
    private OrderItemDto orderItemDto;

    private static final String PATH_TO_Order_XSL = "src/main/resources/com/increff/employee/templateInvoice.xsl";

    @ApiOperation(value = "Adds order item")
    @RequestMapping(path = "/api/order", method = RequestMethod.POST)
    public void add(@RequestBody List<OrderItemForm> form) throws Exception {
        orderItemDto.add(form);
    }

    @ApiOperation(value = "Gets an order item by barcode")
    @RequestMapping(path = "/api/order/{barcode}", method = RequestMethod.GET)

    public OrderItemData get(@PathVariable String barcode) throws ApiException {

        return orderItemDto.getProduct(barcode);
    }

    //invoice generation
    @ApiOperation(value = "Invoice generation")
    @RequestMapping(path = "/api/order/report", method = RequestMethod.GET)
    public void get(HttpServletResponse response) throws Exception {
            byte[] pdfBytes = orderItemDto.generateReport();
            response.setContentType("application/pdf");
            response.setContentLength(pdfBytes.length);
            response.getOutputStream().write(pdfBytes);
            response.getOutputStream().flush();

    }

}
