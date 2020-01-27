package com.increff.pos.dto;

import com.increff.pos.model.ProductData;
import com.increff.pos.model.ProductForm;
import com.increff.pos.pojo.BrandPojo;
import com.increff.pos.pojo.ProductDisplayPojo;
import com.increff.pos.pojo.ProductPojo;
import com.increff.pos.service.ApiException;
import com.increff.pos.service.BrandService;
import com.increff.pos.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductDto {

    @Autowired
    private ProductService productService;

    @Autowired
    private BrandService brandService;


    public void add(ProductForm form) throws ApiException {
        ProductPojo p = convert(form);
        productService.add(p,form.getBrand(),form.getCategory());
    }

    public BrandPojo tempID(String brand , String category) throws ApiException {
        BrandPojo temp_id = brandService.check(brand, category);
        return temp_id;
    }

    public String brandGet(int id) throws ApiException {
        return brandService.getCheck(id).getBrand();
    }

    public String categoryGet(int id) throws ApiException {
        return brandService.getCheck(id).getCategory();
    }

    public ProductData get(int id) throws ApiException {
        ProductPojo p = productService.getCheck(id);
        return convert(p);
    }

    public List<ProductData> getAll() throws ApiException {
        List<ProductDisplayPojo> list = productService.getAll();
        List<ProductData> list2 = new ArrayList<ProductData>();
        for (ProductDisplayPojo p : list) {
            list2.add(convertForGetAll(p));
        }
        return list2;
    }

    public void update(int id, ProductForm f) throws ApiException {
        ProductPojo p = convert(f);
        productService.update(id, p , f.getBrand() ,f.getCategory());
    }

    private ProductData convertForGetAll(ProductDisplayPojo p) {
        ProductData p1 = new ProductData();
        p1.setId(p.getId());
        p1.setName(p.getName());
        p1.setBarcode(p.getBarcode());
        p1.setBrand_category_id(p.getBrand_category_id());
        p1.setBrand(p.getBrand());
        p1.setCategory(p.getCategory());
        p1.setMrp(p.getMrp());
        return p1;
    }

    private ProductData convert(ProductPojo f) throws ApiException {
        ProductData p = new ProductData();
        p.setId(f.getId());
        p.setName(f.getName());
        p.setBarcode(f.getBarcode());
        p.setBrand_category_id(f.getBrand_category_id());
        p.setBrand(productService.getBrand(f.getBrand_category_id()));
        p.setCategory(productService.getCategory(f.getBrand_category_id()));
        p.setMrp(f.getMrp());
        return p;
    }

    private static ProductPojo convert(ProductForm f) {
        ProductPojo p = new ProductPojo();
        p.setName(f.getName());
        p.setBarcode(f.getBarcode());
        p.setMrp(f.getMrp());
        return p;
    }
}


