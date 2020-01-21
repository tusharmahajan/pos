package com.increff.employee.dto;

import com.increff.employee.model.OrderItemData;
import com.increff.employee.model.OrderItemForm;
import com.increff.employee.pojo.InventoryPojo;
import com.increff.employee.pojo.OrderItemPojo;
import com.increff.employee.pojo.OrderPojo;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.transaction.Transactional;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.Date;
import java.util.List;

@Service
public class OrderItemDto {

    public final String xmlFilePath = "./invoice.xml";
    @Autowired
    private ProductService productService;
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private InventoryService inventoryService;

    @Transactional
    public void add(List<OrderItemForm> form) throws ApiException, TransformerException, ParserConfigurationException {
        OrderPojo order = new OrderPojo();
        order.setTime(new Date());
        order = orderService.add(order);
        for (OrderItemForm entry : form) {
//            System.out.println(entry);
            orderItemService.add(convert(order, entry));
        }
        make(form, order.getOrderId());
    }

    public OrderItemData getProduct(String barcode) throws ApiException {
        ProductPojo p = productService.getBarcodePojo(barcode);
        OrderItemData data = new OrderItemData();
        data.setBarcode(barcode);
        data.setMrp(p.getMrp());
        data.setQuantity((inventoryService.getInventoryPojo(p.getProduct_id())).getQuantity());
        data.setName(p.getName());
        return data;
    }

    private OrderItemPojo convert(OrderPojo order, OrderItemForm form) throws ApiException {
        OrderItemPojo p = new OrderItemPojo();
        ProductPojo product = productService.getBarcodePojo(form.getBarcode());
        InventoryPojo inventory = inventoryService.getInventoryPojo(product.getProduct_id());

        p.setOrder_id(order.getOrderId());
        p.setQuantity(form.getQuantity());
        inventory.setQuantity(inventory.getQuantity() - form.getQuantity());

        inventoryService.update(product.getProduct_id(), inventory);
        p.setProduct_id(product.getProduct_id());
        p.setSellingPrice(product.getMrp());
        return p;
    }

    public void make(List<OrderItemForm> form, int id) throws ParserConfigurationException, ApiException, TransformerException {
        DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();

        DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();

        Document document = documentBuilder.newDocument();

        Element root = document.createElement("InvoiceData");
        document.appendChild(root);
        Double sum = 0.0;
        int sno = 1;
        for (OrderItemForm f : form) {
            ProductPojo p = productService.getBarcodePojo(f.getBarcode());
            Element product = document.createElement("invoice");
            root.appendChild(product);
            Element count = document.createElement("sno");
            count.appendChild(document.createTextNode(Integer.toString(sno)));
            product.appendChild(count);
            Element product_name = document.createElement("name");
            product_name.appendChild(document.createTextNode(p.getName()));
            product.appendChild(product_name);
            Element barcode = document.createElement("barcode");
            barcode.appendChild(document.createTextNode(p.getBarcode()));
            product.appendChild(barcode);
            Element qty = document.createElement("qty");
            qty.appendChild(document.createTextNode(Integer.toString(f.getQuantity())));
            product.appendChild(qty);
            Element mrp = document.createElement("mrp");
            mrp.appendChild(document.createTextNode(Double.toString(p.getMrp())));
            product.appendChild(mrp);
            Element totalPrice = document.createElement("totalPrice");
            totalPrice.appendChild(document.createTextNode(Double.toString(f.getQuantity() * p.getMrp())));
            product.appendChild(totalPrice);
            sum += (p.getMrp() * f.getQuantity());
            sno += 1;
        }
        Element totalPrice = document.createElement("totalAmount");
        totalPrice.appendChild(document.createTextNode(Double.toString(sum)));
        root.appendChild(totalPrice);

        Element orderId = document.createElement("ID");
        orderId.appendChild(document.createTextNode(Integer.toString(id)));
        root.appendChild(orderId);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource domSource = new DOMSource(document);
        StreamResult streamResult = new StreamResult(new File(xmlFilePath));

        transformer.transform(domSource, streamResult);


    }


}