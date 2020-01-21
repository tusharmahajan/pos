package com.increff.employee.service;

import com.increff.employee.dto.ProductDto;
import com.increff.employee.dao.ProductDao;
import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import static org.springframework.util.ObjectUtils.isEmpty;

@Service
public class ProductService {

    @Autowired
    private ProductDao dao;

    @Autowired
    private ProductDto productDto;

    @Transactional(rollbackOn = ApiException.class)
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

        BrandPojo temp_id = productDto.tempID(brand ,category);

        if (temp_id != null) {
            p.setBrand_category(temp_id.getId());
           return dao.insert(p);
        }
        throw new ApiException();
    }



    @Transactional(rollbackOn = ApiException.class)
    public ProductPojo get(int id) throws ApiException {
        return getCheck(id);
    }

    @Transactional
    public List<ProductPojo> getAll() {
        return dao.selectAll();
    }

    @Transactional(rollbackOn = ApiException.class)
    public void update(int id, ProductPojo p, String brand, String category) throws ApiException {
        normalize(p);
        ProductPojo ex = getCheck(id);

        BrandPojo temp_id = productDto.tempID(brand , category);
        // to do weather to update barcode
        if(p.getMrp() < 0) throw new ApiException("MRP cant be negative");

        if (temp_id != null && p.getMrp() >0 ) {
            ex.setMrp(p.getMrp());
            ex.setName(p.getName());
            ex.setBarcode(p.getBarcode());
            ex.setBrand_category(temp_id.getId());
            dao.update(ex);
        }
    }

    public String get_brand(int id) throws ApiException {
            return productDto.brandget(id);
    }

    public String get_category(int id) throws ApiException {
        return productDto.categoryget(id);
    }

    public int get_product_id(String barcode) throws ApiException {
        if (dao.selectBar(barcode)==null) throw new ApiException("Product doesn't exist");
            return dao.selectBar(barcode).getProduct_id();

    }

    public ProductPojo getBarcodePojo(String barcode) throws ApiException {
        return dao.selectBar(barcode);
    }

    @Transactional
    public ProductPojo getCheck(int id) throws ApiException {
        ProductPojo p = dao.select(id);

        if (p == null) {
            throw new ApiException("Product with given ID does not exit, id: " + id);
        }
        return p;
    }

    protected static void normalize(ProductPojo p) {
        p.setName(StringUtil.toLowerCase(p.getName()));
        p.setBarcode(StringUtil.toLowerCase(p.getBarcode()));
    }

    public Double getmrp(String barcode) {
        return dao.selectBar(barcode).getMrp();
    }
}
