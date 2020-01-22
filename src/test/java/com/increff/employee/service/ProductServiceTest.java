package com.increff.employee.service;

import com.increff.employee.dao.BrandDao;
import com.increff.employee.dao.ProductDao;
import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.pojo.ProductPojo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class ProductServiceTest extends AbstractUnitTest {

    @Autowired
    ProductService productService;

    @Autowired
    ProductDao productDao;

    @Autowired
    BrandService brandService;

    @Autowired
    BrandDao brandDao;
    BrandPojo b = new BrandPojo();
    ProductPojo p1 = new ProductPojo();

    @Test
    public void testProduct1() {
        ProductPojo b = new ProductPojo();

        b.setBarcode("871");
        assertEquals("871", b.getBarcode());
    }

    @Test
    public void testProduct2() {
        ProductPojo b = new ProductPojo();

        b.setBarcode("871");
        assertEquals("871", b.getBarcode());
    }

    @Test
    public void addTest() throws ApiException {
        ProductPojo p = new ProductPojo();
        p.setBarcode("9000");
        p.setName("5 star");
        p.setMrp(90.0);

        BrandPojo b = new BrandPojo();
        b.setBrand("hp");
        b.setCategory("envy");

        b = brandService.add(b);
        p.setBrand_category(b.getId());

        productService.add(p, "hp", "envy");

    }

    @Test(expected = ApiException.class)
    public void nullbrandtest() throws ApiException {
        ProductPojo p = new ProductPojo();
        p.setBarcode("9000");
        p.setName("5 star");
        p.setMrp(90.0);

        BrandPojo b = new BrandPojo();
        b.setBrand("hp");
        b.setCategory("envy");
        productService.add(p, "", "envy");

    }

    @Test(expected = ApiException.class)
    public void nullcategorytest() throws ApiException {
        ProductPojo p = new ProductPojo();
        p.setBarcode("9000");
        p.setName("5 star");
        p.setMrp(90.0);

        BrandPojo b = new BrandPojo();
        b.setBrand("hp");
        b.setCategory("envy");
        productService.add(p, "hp", "");

    }

    @Test(expected = ApiException.class)
    public void nullnametest() throws ApiException {
        ProductPojo p = new ProductPojo();
        p.setBarcode("9000");
        p.setName("");
        p.setMrp(90.0);

        BrandPojo b = new BrandPojo();
        b.setBrand("hp");
        b.setCategory("envy");
        productService.add(p, "hp", "envy");

    }

    @Test(expected = ApiException.class)
    public void nullbarcodetest() throws ApiException {
        ProductPojo p = new ProductPojo();
        p.setBarcode("");
        p.setName("oil1");
        p.setMrp(90.0);

        BrandPojo b = new BrandPojo();
        b.setBrand("hp");
        b.setCategory("envy");
        productService.add(p, "hp", "envy");

    }

    @Test(expected = ApiException.class)
    public void negativemrptest() throws ApiException {
        ProductPojo p = new ProductPojo();
        p.setBarcode("");
        p.setName("oil1");
        p.setMrp(-10.0);

        BrandPojo b = new BrandPojo();
        b.setBrand("hp");
        b.setCategory("envy");

        productService.add(p, "hp", "envy");

    }

    @Test(expected = ApiException.class)
    public void nullmrptest() throws ApiException {
        ProductPojo p = new ProductPojo();
        p.setBarcode("");
        p.setName("oil1");
        p.setMrp(null);

        BrandPojo b = new BrandPojo();
        b.setBrand("hp");
        b.setCategory("envy");
        productService.add(p, "hp", "envy");

    }

    @Test
    public void testupdate() throws ApiException {
        ProductPojo p = new ProductPojo();
        p.setBarcode("900");
        p.setName("oil");
        p.setMrp(10.0);

        BrandPojo b = new BrandPojo();
        b.setBrand("hp");
        b.setCategory("envy");
        b = brandService.add(b);
        p = productService.add(p, "hp", "envy");

        ProductPojo p1 = new ProductPojo();
        p1.setBarcode("800");
        p1.setName("butter");
        p1.setMrp(11.0);
        p1.setProduct_id(1);
        p1.setBrand_category(b.getId());
        productService.update(p.getProduct_id(), p1, "hp", "envy");
    }

    @Test(expected = ApiException.class)
    public void testupdateBarcode() throws ApiException {
        ProductPojo p = new ProductPojo();
        p.setBarcode("800");
        p.setName("oil");
        p.setMrp(10.0);

        BrandPojo b = new BrandPojo();
        b.setBrand("hp");
        b.setCategory("envy");
        b = brandService.add(b);
        p = productService.add(p, "hp", "envy");

        ProductPojo p1 = new ProductPojo();
        p1.setBarcode("800");
        p1.setName("butter");
        p1.setMrp(11.0);
        p1.setProduct_id(1);
        productService.update(p1.getProduct_id(), p, "hp", "envy");
    }


    @Test
    public void categoryTest() throws ApiException {
        ProductPojo p = new ProductPojo();
        p.setBarcode("800");
        p.setName("oil");
        p.setMrp(10.0);

        BrandPojo b = new BrandPojo();
        b.setBrand("hp");
        b.setCategory("envy");
        b = brandService.add(b);

        p = productService.add(p, "hp", "envy");
        assertEquals(productService.get_category(p.getBrand_category()) ,b.getCategory());
    }

    @Test
    public void brandTest() throws ApiException {
        ProductPojo p = new ProductPojo();
        p.setBarcode("800");
        p.setName("oil");
        p.setMrp(10.0);

        BrandPojo b = new BrandPojo();
        b.setBrand("hp");
        b.setCategory("envy");
        b = brandService.add(b);
        p.setBrand_category(b.getId());

        p = productService.add(p, "hp", "envy");
        assertEquals(productService.get_brand(p.getBrand_category()) ,b.getBrand());
    }

    @Test
    public void productIdtest() throws ApiException {
        ProductPojo p = new ProductPojo();
        p.setBarcode("800");
        p.setName("oil");
        p.setMrp(10.0);

        BrandPojo b = new BrandPojo();
        b.setBrand("hp");
        b.setCategory("envy");
        b = brandService.add(b);
        p.setBrand_category(b.getId());

        p = productService.add(p, "hp", "envy");
        assertEquals(productService.get_product_id(p.getBarcode()), p.getProduct_id());
    }

    @Test(expected = ApiException.class)
    public void errorProductIdtest() throws ApiException {
        ProductPojo p = new ProductPojo();
        p.setName("oil");
        p.setMrp(10.0);

        BrandPojo b = new BrandPojo();
        b.setBrand("hp");
        b.setCategory("envy");
        b = brandService.add(b);
        p.setBrand_category(b.getId());

        p = productService.add(p, "hp", "envy");
        assertEquals(productService.get_product_id("900"), p.getProduct_id());
    }

    @Test
    public void getTest() throws ApiException {
        ProductPojo p = new ProductPojo();
        p.setName("oil");
        p.setMrp(10.0);
        p.setBarcode("800");
        BrandPojo b = new BrandPojo();
        b.setBrand("hp");
        b.setCategory("envy");
        b = brandService.add(b);
        p.setBrand_category(b.getId());

        p = productService.add(p, "hp", "envy");

        ProductPojo p1 = productService.get(p.getProduct_id());
        assertEquals(p.getProduct_id(), p1.getProduct_id());
    }

    @Test
    public void getMrpTest() throws ApiException {
        ProductPojo p = new ProductPojo();
        p.setName("oil");
        p.setMrp(10.0);
        p.setBarcode("800");

        BrandPojo b = new BrandPojo();
        b.setBrand("hp");
        b.setCategory("envy");
        b = brandService.add(b);
        p.setBrand_category(b.getId());

        p = productService.add(p, "hp", "envy");
        ProductPojo p1 = productService.get(p.getProduct_id());

        assertEquals(productService.getmrp(p.getBarcode()), p1.getMrp());
    }

    @Test
    public void getAllTest() throws ApiException {

        List<ProductPojo> pp = new ArrayList<>();

        BrandPojo b = new BrandPojo();
        b.setBrand("hp");
        b.setCategory("envy");
        b = brandService.add(b);

        for(int i = 1;i<3;i++) {
            ProductPojo p = new ProductPojo();
            p.setName("oil" + i);
            p.setMrp(10.0 * i);
            p.setBarcode("800" + i);
            p.setBrand_category(b.getId());
            p = productService.add(p, "hp", "envy");
        }

        assertEquals(2,productService.getAll().size());
    }

    @Test
    public void getBarcodePojoTest() throws ApiException {

        ProductPojo p = new ProductPojo();
        p.setName("oil");
        p.setMrp(10.0);
        p.setBarcode("800");

        BrandPojo b = new BrandPojo();
        b.setBrand("hp");
        b.setCategory("envy");

        b = brandService.add(b);
        p.setBrand_category(b.getId());
        p = productService.add(p , "hp" , "envy");

        ProductPojo p1 = productService.getBarcodePojo(p.getBarcode());

        assertEquals(p.getProduct_id() , p1.getProduct_id());
    }

}
