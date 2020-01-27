package com.increff.pos.controller;


import com.increff.pos.dto.InventoryReportDto2;
import com.increff.pos.model.InventoryReportData;
import com.increff.pos.service.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api
@RestController
public class InventoryReportApicontroller {

    @Autowired
    private InventoryReportDto2 inventoryReportDto2;

    @ApiOperation(value = "Inventory report")
    @RequestMapping(path = "/api/inventoryreport", method = RequestMethod.GET)
    public List<InventoryReportData> get() throws ApiException {
        return inventoryReportDto2.getAll();
    }
}
