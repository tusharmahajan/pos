package com.increff.employee.controller;

import com.increff.employee.dto.ProductDto;
import com.increff.employee.model.ProductData;
import com.increff.employee.model.ProductForm;
import com.increff.employee.service.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api
@RestController
public class ProductApiController {


    @Autowired
    private ProductDto productDto;

    @ApiOperation(value = "Adds product")
    @RequestMapping(path = "/api/product", method = RequestMethod.POST)
    public void add(@RequestBody ProductForm form) throws ApiException {
        productDto.add(form);
    }


    @ApiOperation(value = "Gets an product by ID")
    @RequestMapping(path = "/api/product/{id}", method = RequestMethod.GET)

    public ProductData get(@PathVariable int id) throws ApiException {

        return productDto.get(id);
    }

    @ApiOperation(value = "Gets list of all products")
    @RequestMapping(path = "/api/product", method = RequestMethod.GET)
    public List<ProductData> getAll() throws ApiException {

        return productDto.getAll();
    }

    @ApiOperation(value = "Updates an product")
    @RequestMapping(path = "/api/product/{id}", method = RequestMethod.PUT)
    public void update(@PathVariable int id, @RequestBody ProductForm f) throws ApiException {
        productDto.update(id, f);
    }


}


