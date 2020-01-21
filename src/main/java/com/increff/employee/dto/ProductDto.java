package com.increff.employee.dto;

import com.increff.employee.model.ProductData;
import com.increff.employee.model.ProductForm;
import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.BrandService;
import com.increff.employee.service.ProductService;
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

    public String brandget(int id) throws ApiException {
        return brandService.get(id).getBrand();
    }

    public String categoryget(int id) throws ApiException {
        return brandService.get(id).getCategory();
    }

    public ProductData get(int id) throws ApiException {
        ProductPojo p = productService.get(id);
        return convert(p);
    }

    public List<ProductData> getAll() throws ApiException {
        List<ProductPojo> list = productService.getAll();
        List<ProductData> list2 = new ArrayList<ProductData>();
        for (ProductPojo p : list) {
            list2.add(convert(p));
        }
        return list2;
    }

    public void update(int id, ProductForm f) throws ApiException {
        ProductPojo p = convert(f);
        productService.update(id, p , f.getBrand() ,f.getCategory());
    }

    private ProductData convert(ProductPojo f) throws ApiException {
        ProductData p = new ProductData();
        p.setProduct_id(f.getProduct_id());
        p.setName(f.getName());
        p.setBarcode(f.getBarcode());
        p.setBrand_category(f.getBrand_category());
        p.setBrand(productService.get_brand(f.getBrand_category()));
        p.setCategory(productService.get_category(f.getBrand_category()));
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


