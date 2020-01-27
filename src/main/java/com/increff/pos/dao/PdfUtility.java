package com.increff.pos.dao;

import com.increff.pos.model.OrderItemData;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.List;

public class PdfUtility {
    private static final String PATH_TO_Order_XSL = "src/main/resources/com/increff/pos/templateInvoice.xsl" ;
    public static byte[] createPdfForInvoice(List<OrderItemData> itemData, int id) throws Exception {
        DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();

        DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();

        Document document = documentBuilder.newDocument();
        Element root = document.createElement("InvoiceData");
        document.appendChild(root);
        Double sum=0.0;
        int sno=1;
        for(OrderItemData data:itemData)
        {
            Element product = document.createElement("invoice");
            root.appendChild(product);
            Element count = document.createElement("sno");
            count.appendChild(document.createTextNode(Integer.toString(sno)));
            product.appendChild(count);
            Element product_name = document.createElement("name");
            product_name.appendChild(document.createTextNode(data.getName()));
            product.appendChild(product_name);
            Element barcode = document.createElement("barcode");
            barcode.appendChild(document.createTextNode(data.getBarcode()));
            product.appendChild(barcode);
            Element qty = document.createElement("qty");
            qty.appendChild(document.createTextNode(Integer.toString(data.getQuantity())));
            product.appendChild(qty);
            Element mrp = document.createElement("mrp");
            mrp.appendChild(document.createTextNode(Double.toString(data.getMrp())));
            product.appendChild(mrp);
            Element totalPrice = document.createElement("totalPrice");
            totalPrice.appendChild(document.createTextNode(Double.toString(data.getQuantity()*data.getMrp())));
            product.appendChild(totalPrice);
            sum+=(data.getMrp()*data.getQuantity());
            sno+=1;
        }
        Element totalPrice=document.createElement("totalAmount");
        totalPrice.appendChild(document.createTextNode(Double.toString(sum)));
        root.appendChild(totalPrice);

        Element orderId=document.createElement("ID");
        orderId.appendChild(document.createTextNode(Integer.toString(id)));
        root.appendChild(orderId);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource domSource = new DOMSource(document);

        ByteArrayOutputStream xmlBaos = new ByteArrayOutputStream();
        StreamResult streamResult = new StreamResult(xmlBaos);
        transformer.transform(domSource, streamResult);

        FopFactory fopFactory = FopFactory.newInstance(new File(".").toURI());
        ByteArrayOutputStream pdfBaos = new ByteArrayOutputStream();
        Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, pdfBaos);
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer2 = factory.newTransformer(new StreamSource(PATH_TO_Order_XSL));
        Result res = new SAXResult(fop.getDefaultHandler());
        Source src = new StreamSource(new ByteArrayInputStream(xmlBaos.toByteArray()));
        transformer2.transform(src, res);
        return pdfBaos.toByteArray();


    }
}

