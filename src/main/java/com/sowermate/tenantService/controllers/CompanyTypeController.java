package com.sowermate.tenantService.controllers;

import com.sowermate.tenantService.entities.value.CompanyTypeValue;
import com.sowermate.tenantService.services.CompanyTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/company_types")
@CrossOrigin(origins = "http://localhost:3000/")
public class CompanyTypeController {

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
        CompanyTypeValue companyTypeValue=companyTypeService.getCompanyType(tenantUuid,companyTypeUuid);
        return  new ResponseEntity<>(companyTypeValue, HttpStatus.ACCEPTED);
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
    public ResponseEntity<CompanyTypeValue> deleteCompanyType(@PathVariable String  tenantUuid,@PathVariable String companyTypeUuid) {
        CompanyTypeValue companyTypeValue =companyTypeService.deleteCompanyType(tenantUuid,companyTypeUuid);
        return new ResponseEntity<CompanyTypeValue>(companyTypeValue ,HttpStatus.ACCEPTED);
    }

}
