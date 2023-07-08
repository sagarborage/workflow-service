package com.sowermate.tenantService.controllers;

import com.sowermate.tenantService.entities.value.AddressValue;
import com.sowermate.tenantService.entities.value.CompanyAddressValue;
import com.sowermate.tenantService.entities.value.ProFormaInvoiceItemValue;
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
  private static final org.slf4j.Logger Logger= LoggerFactory.getLogger(CompanyAddressController.class);

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<CompanyAddressValue> createCompanyAddress(@RequestBody CompanyAddressValue  companyAddressValue) {
        try {
            companyAddressValue = companyAddressService.createCompanyAddress(companyAddressValue);
        } catch (Exception e) {
            Logger.error("Error while creating Seller:", e);
        }
        return new ResponseEntity<CompanyAddressValue>(companyAddressValue, HttpStatus.CREATED);
    }
    @RequestMapping(method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<CompanyAddressValue> editCompanyAddress(@RequestBody CompanyAddressValue companyAddressValue) {

        try {
            companyAddressValue = companyAddressService.editCompanyAddress(companyAddressValue);
        } catch (Exception e) {
            Logger.error("Error while editing Seller:", e);
        }
        return new ResponseEntity<CompanyAddressValue>(companyAddressValue, HttpStatus.CREATED);
    }

    @GetMapping("/{tenantUuid}/{companyAddressUuid}")
    public ResponseEntity<CompanyAddressValue> getCompanyAddress(@PathVariable String tenantUuid,
                                                          @PathVariable String companyAddressUuid) {
        CompanyAddressValue companyAddressValue = null;
        try {
            companyAddressValue = companyAddressService.getCompanyAddress(tenantUuid,companyAddressUuid);
        } catch (Exception e) {
            Logger.error("Error while getting Seller:", e);
        }
        return new ResponseEntity<>(companyAddressValue, HttpStatus.ACCEPTED);
    }

    @GetMapping("/{tenantUuid}")
    public ResponseEntity<List<CompanyAddressValue>> getAllCompanyAddress(@PathVariable String tenantUuid) {
        List<CompanyAddressValue> companyAddressValues = null;
        try {
            companyAddressValues = companyAddressService.getAllCompanyAddress(tenantUuid);
            Logger.info("records " + companyAddressValues.size());
        } catch (Exception e) {
            Logger.error("Error while getting Seller:", e);
        }
        return new ResponseEntity<>(companyAddressValues, HttpStatus.ACCEPTED);
    }
    @RequestMapping(value = "/{tenantUuid}/{companyAddressUuid}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<Integer> deleteCompanyAddress(@PathVariable String tenantUuid,@PathVariable String companyAddressUuid) throws Exception {
        int deleteCompanyAddress=0;
        deleteCompanyAddress=companyAddressService.deleteCompanyAddress(tenantUuid,companyAddressUuid);
        return new ResponseEntity<Integer>( deleteCompanyAddress,HttpStatus.ACCEPTED);
    }

}
