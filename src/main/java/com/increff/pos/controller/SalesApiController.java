package com.increff.pos.controller;

import com.increff.pos.dto.SalesReportDto2;
import com.increff.pos.model.SalesReportData;
import com.increff.pos.model.SalesReportForm;
import com.increff.pos.service.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api
@RestController
public class SalesApiController {

    @Autowired
    private SalesReportDto2 salesReportDto2;

    @ApiOperation(value = "Gets sales reports")
    @RequestMapping(path = "/api/salesreport", method = RequestMethod.POST)
    public List<SalesReportData> get(@RequestBody SalesReportForm form) throws ApiException {
        return salesReportDto2.get(form);
    }


}
