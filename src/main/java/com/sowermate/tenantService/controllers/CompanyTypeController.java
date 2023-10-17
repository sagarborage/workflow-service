package com.sowermate.tenantService.controllers;

import com.sowermate.tenantService.entities.value.CompanyTypeValue;
import com.sowermate.tenantService.entities.value.CompanyValue;
import com.sowermate.tenantService.payload.ApiResponse;
import com.sowermate.tenantService.services.CompanyTypeService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/company_types")
public class CompanyTypeController {

    private static final org.slf4j.Logger Logger= LoggerFactory.getLogger(CompanyTypeController.class);
    @Autowired
    private CompanyTypeService companyTypeService;

    @RequestMapping( method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<CompanyTypeValue> createCompanyType(@RequestBody CompanyTypeValue companyTypeValue) {
        CompanyTypeValue companyTypeValue1=companyTypeService.createCompanyType(companyTypeValue);
        return new ResponseEntity<CompanyTypeValue>( companyTypeValue1, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{tenantUuid}/{companyTypeUuid}")
    public ResponseEntity<CompanyTypeValue> getCompanyType(@PathVariable String  tenantUuid,@PathVariable String companyTypeUuid) {

        CompanyTypeValue companyTypeValue=null;
        try {
             companyTypeValue=companyTypeService.getCompanyType(tenantUuid,companyTypeUuid);

        } catch (Exception e) {
            Logger.error("Error while creating Seller:", e);
        }
        return new ResponseEntity<CompanyTypeValue>(companyTypeValue,HttpStatus.CREATED);

    }

    @GetMapping(value = "/{tenantUuid}")
    public ResponseEntity<List<CompanyTypeValue>> getAllCompanyType(@PathVariable String  tenantUuid) {
        List<CompanyTypeValue> companyTypeValues = companyTypeService.getAllCompanyType(tenantUuid);
        return new ResponseEntity<> (companyTypeValues ,HttpStatus.ACCEPTED);
    }

    @RequestMapping( method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<CompanyTypeValue> editCompanyType(@RequestBody CompanyTypeValue companyTypeValue) throws Exception{
        CompanyTypeValue companyTypeValue1=companyTypeService.editCompanyType(companyTypeValue);
        return new ResponseEntity<CompanyTypeValue> (companyTypeValue1, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{tenantUuid}/{companyTypeUuid}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<ApiResponse> deleteCompanyType(@PathVariable String  tenantUuid, @PathVariable String companyTypeUuid) {
        CompanyTypeValue companyTypeValue =companyTypeService.deleteCompanyType(tenantUuid,companyTypeUuid);
        return new ResponseEntity<> (new  ApiResponse("CompanyType Deleted Successfully",true),HttpStatus.OK);
    }

}
