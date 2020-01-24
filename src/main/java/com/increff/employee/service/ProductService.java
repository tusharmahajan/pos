package com.increff.employee.service;

import com.increff.employee.dao.ProductDao;
import com.increff.employee.dto.ProductDto;
import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.util.ObjectUtils.isEmpty;

@Service
public class ProductService {

    @Autowired
    private ProductDao dao;

    @Autowired
    private ProductDto productDto;

    protected static void normalize(ProductPojo p) {
        p.setName(StringUtil.toLowerCase(p.getName()));
        p.setBarcode(StringUtil.toLowerCase(p.getBarcode()));
    }

    @Transactional(rollbackFor = ApiException.class)
    public ProductPojo add(ProductPojo p, String brand, String category) throws ApiException {
        normalize(p);
        if (StringUtil.isEmpty(p.getBarcode())) {
            throw new ApiException("Barcode cannot be empty");
        }
        if (isEmpty(p.getMrp())) {
            throw new ApiException("Mrp cannot be empty");
        }

        if (StringUtil.isEmpty(p.getName())) {
            throw new ApiException("name cannot be empty");
        }

        if (p.getMrp() < 0) throw new ApiException("MRP cannot be negative");
        BrandPojo temp_id = productDto.tempID(brand, category);

        p.setBrand_category(temp_id.getId());
        return dao.insert(p);

    }

    @Transactional(readOnly = true)
    public ProductPojo get(int id){
        return dao.select(id);
    }

    @Transactional(readOnly = true)
    public List<ProductPojo> getAll() {
        return dao.selectAll();
    }

    @Transactional(rollbackFor = ApiException.class)
    public void update(int id, ProductPojo p, String brand, String category) throws ApiException {
        normalize(p);
        ProductPojo ex = getCheck(id);
        BrandPojo temp_id = productDto.tempID(brand, category);

        if (p.getMrp() < 0) throw new ApiException("MRP cant be negative");
        if (dao.selectBar(p.getBarcode()) != null) throw new ApiException("Enter unique barcode");

        if (temp_id != null) {
            ex.setMrp(p.getMrp());
            ex.setName(p.getName());
            ex.setBarcode(p.getBarcode());
            ex.setBrand_category(temp_id.getId());
            dao.update(ex);
        }
    }

    public String getBrand(int id) throws ApiException {
        return productDto.brandGet(id);
    }

    public String getCategory(int id) throws ApiException {
        return productDto.categoryGet(id);
    }

    public int getProductId(String barcode) throws ApiException {
        if (dao.selectBar(barcode) == null) throw new ApiException("Product doesn't exist");
        return dao.selectBar(barcode).getProduct_id();
    }

    public ProductPojo getBarcodePojo(String barcode) throws ApiException {
        return dao.selectBar(barcode);
    }

    @Transactional(rollbackFor = ApiException.class)
    public ProductPojo getCheck(int id) throws ApiException {
        ProductPojo p = get(id);
        if (p == null) throw new ApiException("Product with given ID does not exit, id: " + id);
        return p;
    }

    public Double getMrp(String barcode) {
        return dao.selectBar(barcode).getMrp();
    }
}
