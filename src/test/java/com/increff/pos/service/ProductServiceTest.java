package com.increff.pos.service;

import com.increff.pos.dao.BrandDao;
import com.increff.pos.dao.ProductDao;
import com.increff.pos.pojo.BrandPojo;
import com.increff.pos.pojo.ProductPojo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNull;

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
        p.setBrand_category_id(b.getId());

        productService.add(p, "hp", "envy");

    }

//    @Test
//    public void nulladdTest() throws ApiException {
//        ProductPojo p = new ProductPojo();
//        p.setBarcode("9000");
//        p.setName("5 star");
//        p.setMrp(90.0);
//
//        BrandPojo b = new BrandPojo();
//        b.setBrand("hp");
//        b.setCategory("envy");
//
//        b = brandService.add(b);
//        p.setBrand_category(11);
//
//       assertEquals(null , productService.add(p, "hp1", "envy"));
////        assertNull(p);
//    }
    @Test(expected = ApiException.class)
    public void nullBrandTest() throws ApiException {
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
    public void nullCategoryTest() throws ApiException {
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
    public void nullNameTest() throws ApiException {
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
    public void nullBarcodeTest() throws ApiException {
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
    public void nullMrpTest() throws ApiException {
        ProductPojo p = new ProductPojo();
        p.setBarcode("989");
        p.setName("oil1");
        p.setMrp(null);

        BrandPojo b = new BrandPojo();
        b.setBrand("hp");
        b.setCategory("envy");
        productService.add(p, "hp", "envy");

    }
    @Test(expected = ApiException.class)
    public void negativeMrpTest() throws ApiException {
        ProductPojo p = new ProductPojo();
        p.setBarcode("");
        p.setName("oil1");
        p.setMrp(-10.0);

        BrandPojo b = new BrandPojo();
        b.setBrand("hp");
        b.setCategory("envy");

        productService.add(p, "hp", "envy");

    }

    @Test
    public void testUpdate() throws ApiException {
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
        p1.setId(1);
        p1.setBrand_category_id(b.getId());
        productService.update(p.getId(), p1, "hp", "envy");
    }

    @Test(expected = ApiException.class)
    public void testUpdateBarcode() throws ApiException {
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
        p1.setId(1);
        productService.update(p1.getId(), p, "hp", "envy");
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
        assertEquals(productService.getCategory(p.getBrand_category_id()) ,b.getCategory());
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
        p.setBrand_category_id(b.getId());

        p = productService.add(p, "hp", "envy");
        assertEquals(productService.getBrand(p.getBrand_category_id()) ,b.getBrand());
    }

    @Test
    public void productIdTest() throws ApiException {
        ProductPojo p = new ProductPojo();
        p.setBarcode("800");
        p.setName("oil");
        p.setMrp(10.0);

        BrandPojo b = new BrandPojo();
        b.setBrand("hp");
        b.setCategory("envy");
        b = brandService.add(b);
        p.setBrand_category_id(b.getId());

        p = productService.add(p, "hp", "envy");
        assertEquals(productService.getProductId(p.getBarcode()), p.getId());
    }

    @Test(expected = ApiException.class)
    public void errorProductIdTest() throws ApiException {
        ProductPojo p = new ProductPojo();
        p.setName("oil");
        p.setMrp(10.0);

        BrandPojo b = new BrandPojo();
        b.setBrand("hp");
        b.setCategory("envy");
        b = brandService.add(b);
        p.setBrand_category_id(b.getId());

        p = productService.add(p, "hp", "envy");
        assertEquals(productService.getProductId("900"), p.getId());
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
        p.setBrand_category_id(b.getId());

        p = productService.add(p, "hp", "envy");

        ProductPojo p1 = productService.getCheck(p.getId());
        assertEquals(p.getId(), p1.getId());
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
        p.setBrand_category_id(b.getId());

        p = productService.add(p, "hp", "envy");
        ProductPojo p1 = productService.getCheck(p.getId());

        assertEquals(productService.getMrp(p.getBarcode()), p1.getMrp());
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
            p.setBrand_category_id(b.getId());
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
        p.setBrand_category_id(b.getId());
        p = productService.add(p , "hp" , "envy");

        ProductPojo p1 = productService.getBarcodePojo(p.getBarcode());

        assertEquals(p.getId() , p1.getId());
    }

    @Test(expected = ApiException.class)
    public void testUpdate1() throws ApiException {
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
        p1.setBarcode("900");
        p1.setName("butter");
        p1.setMrp(11.0);
        p1.setId(1);
        p1.setBrand_category_id(b.getId());
        productService.update(p.getId(), p1, "hp", "envy");
    }
}
