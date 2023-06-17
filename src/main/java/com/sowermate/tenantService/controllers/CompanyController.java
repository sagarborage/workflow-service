package com.sowermate.tenantService.controllers;
import com.sowermate.tenantService.entities.value.CommonValue;
import com.sowermate.tenantService.entities.value.CompanyValue;
import com.sowermate.tenantService.services.CompanyService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    @Autowired
    private CompanyService companyService;

    private static final org.slf4j.Logger Logger= LoggerFactory.getLogger(CompanyController.class);
    @RequestMapping( method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<CommonValue> createCompanyDetails(@RequestBody CompanyValue companyValue){
        CommonValue commonValue=null;
        try {
            commonValue=companyService.createCompany(companyValue);
        } catch (Exception e) {
            Logger.error("Error while creating Seller:", e);
        }
        return new ResponseEntity<CommonValue>(commonValue,HttpStatus.CREATED);
    }
    @RequestMapping( method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<CommonValue> editCompanyDetails(@RequestBody CompanyValue companyValue){
        CommonValue commonValue=null;
        try {
            commonValue=  companyService.editCompany(companyValue);
        } catch (Exception e) {
            Logger.error("Error while editing Seller:", e);
        }
        return new ResponseEntity<CommonValue>(commonValue,HttpStatus.CREATED);
    }
    @GetMapping("/{uuid}")
    public ResponseEntity<CompanyValue> getCompanyDetails(
            @PathVariable String uuid) {
        CompanyValue companyValue = null;
        try {
            companyValue = companyService.getCompany(uuid);
        } catch (Exception e) {
            Logger.error("Error while getting Seller:", e);
        }
        return new ResponseEntity<>(companyValue, HttpStatus.ACCEPTED);
    }
    @RequestMapping(value = "/{uuid}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<CommonValue> deleteCompanyDetails(
            @PathVariable String uuid) {
        CommonValue commonValue = null;
        try {
            commonValue = companyService.deleteCompany(uuid);
        } catch (Exception e) {
            Logger.error("Error while deleting Seller:", e);
        }
        return new ResponseEntity<CommonValue>(commonValue, HttpStatus.ACCEPTED);
    }

    @GetMapping
    public ResponseEntity<List<CompanyValue>> getAllCompanies() {
        List<CompanyValue> companyValues = null;
        try {
            companyValues = companyService.getAllCompany();
            Logger.info("records " + companyValues.size());
        } catch (Exception e) {
            Logger.error("Error while getting Seller:", e);
        }
        return new ResponseEntity<>(companyValues, HttpStatus.ACCEPTED);
    }



















}
