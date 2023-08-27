package com.sowermate.tenantService.controllers;

import com.sowermate.tenantService.entities.value.*;
import com.sowermate.tenantService.services.CompanyAddressService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/company-address")
public class CompanyAddressController {
  @Autowired
  private CompanyAddressService companyAddressService;

    @RequestMapping( method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<CompanyAddressValue> createCompanyAddress(@RequestBody CompanyAddressValue companyAddressValue) throws Exception {
        CompanyAddressValue companyAddressValue1=companyAddressService.createCompanyAddress(companyAddressValue);
        return new ResponseEntity<CompanyAddressValue>( companyAddressValue1, HttpStatus.CREATED);
    }
    @GetMapping(value = "/{companyAddressUuid}")
    public ResponseEntity<CompanyAddressValue> getCompanyAddress(@PathVariable String companyAddressUuid) throws Exception {
        CompanyAddressValue companyAddressValue=companyAddressService.getCompanyAddress(companyAddressUuid);
        return  new ResponseEntity<>(companyAddressValue, HttpStatus.ACCEPTED);
    }

/*    @GetMapping(value = "/{tenantUuid}")
    public ResponseEntity<List<CompanyAddressValue>> getAllCompanyAddress(@PathVariable String  tenantUuid) throws Exception {
        List<CompanyAddressValue> companyAddressValues = companyAddressService.getAllCompanyAddress(tenantUuid);
        return new ResponseEntity<> (companyAddressValues ,HttpStatus.ACCEPTED);
    }*/

    /*@RequestMapping(value = "/{tenantUuid}/{companyAddressUuid}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<CompanyAddressValue> deleteCompanyAddress(@PathVariable String  tenantUuid,@PathVariable String companyAddressUuid) throws Exception {
        CompanyAddressValue companyAddressValue =companyAddressService.deleteCompanyAddress(tenantUuid,companyAddressUuid);
        return new ResponseEntity<CompanyAddressValue>(companyAddressValue ,HttpStatus.ACCEPTED);
    }
    */

    @RequestMapping( method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<CompanyAddressValue> editCompanyAddress(@RequestBody CompanyAddressValue companyAddressValue) throws Exception{
        CompanyAddressValue companyAddressValue1=companyAddressService.editCompanyAddress(companyAddressValue);
        return new ResponseEntity<CompanyAddressValue> (companyAddressValue1, HttpStatus.CREATED);
    }

}
