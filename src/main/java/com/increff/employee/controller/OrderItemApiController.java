package com.increff.employee.controller;


import com.increff.employee.dto.OrderItemDto;
import com.increff.employee.model.OrderItemData;
import com.increff.employee.model.OrderItemForm;
import com.increff.employee.service.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.List;

@Api
@RestController
public class OrderItemApiController {


    private static final String PATH_TO_Order_XSL = "./templateInvoice.xsl";
    @Autowired
    private OrderItemDto orderItemDto;

    @ApiOperation(value = "Adds order item")
    @RequestMapping(path = "/api/order", method = RequestMethod.POST)
    public void add(@RequestBody List<OrderItemForm> form) throws ApiException, TransformerException, ParserConfigurationException {
        orderItemDto.add(form);
    }

    //
    @ApiOperation(value = "Gets an order item by barcode")
    @RequestMapping(path = "/api/order/{barcode}", method = RequestMethod.GET)

    public OrderItemData get(@PathVariable String barcode) throws ApiException {

        return orderItemDto.getProduct(barcode);
    }

//    @ApiOperation(value = "Gets list of all orders")
//    @RequestMapping(path = "/api/order", method = RequestMethod.GET)
//    public List<Order> getAll() throws ApiException {
//
//
//    }

//    @ApiOperation(value = "Updates an quantity")
//    @RequestMapping(path = "/api/order/{barcode}", method = RequestMethod.PUT)
//    public void update(@PathVariable String barcode) throws ApiException {
//        orderItemDto.update(barcode);
//    }

    //invoice genration
    @ApiOperation(value = "Invoice generation")
    @RequestMapping(path = "/api/order/report", method = RequestMethod.GET)
    public void get(HttpServletResponse response) throws ApiException {
        try {

            FopFactory fopFactory = FopFactory.newInstance(new File(".").toURI());

            ByteArrayOutputStream out = new ByteArrayOutputStream();

            Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, out);
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer(new StreamSource(PATH_TO_Order_XSL));

            //Make sure the XSL transformation's result is piped through to FOP
            Result res = new SAXResult(fop.getDefaultHandler());

            //Setup input
            Source src = new StreamSource(new File("./invoice.xml"));

            transformer.transform(src, res);
            response.setContentType("application/pdf");
            response.setContentLength(out.size());

            response.getOutputStream().write(out.toByteArray());
            response.getOutputStream().flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
