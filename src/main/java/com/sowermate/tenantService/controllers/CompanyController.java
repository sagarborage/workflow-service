package com.sowermate.tenantService.controllers;
import com.sowermate.tenantService.entities.value.CommonValue;
import com.sowermate.tenantService.entities.value.CompanyValue;
import com.sowermate.tenantService.services.CompanyService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/companies")
@CrossOrigin(origins = "http://localhost:3000")
public class CompanyController {
    @Autowired
    private CompanyService companyService;

    private static final org.slf4j.Logger Logger= LoggerFactory.getLogger(CompanyController.class);
    @RequestMapping( method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<CompanyValue> createCompanyDetails(@RequestBody CompanyValue companyValue){
        CompanyValue companyValue1=null;
        try {
            companyValue1=companyService.createCompany(companyValue);
        } catch (Exception e) {
            Logger.error("Error while creating Seller:", e);
        }
        return new ResponseEntity<CompanyValue>(companyValue1,HttpStatus.CREATED);
    }
    @RequestMapping( method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<CompanyValue> editCompanyDetails(@RequestBody CompanyValue companyValue){
        CompanyValue companyValue1=null;
        try {
            companyValue1=  companyService.editCompany(companyValue);
        } catch (Exception e) {
            Logger.error("Error while editing Seller:", e);
        }
        return new ResponseEntity< CompanyValue>(companyValue1,HttpStatus.CREATED);
    }
    @GetMapping("/{tenantUuid}/{companyUuid}")
    public ResponseEntity<CompanyValue> getCompanyDetails(@PathVariable String tenantUuid,
            @PathVariable String companyUuid) {
        CompanyValue companyValue = null;
        try {
            companyValue = companyService.getCompany(tenantUuid,companyUuid);
        } catch (Exception e) {
            Logger.error("Error while getting Seller:", e);
        }
        return new ResponseEntity<>(companyValue, HttpStatus.ACCEPTED);
    }
    @RequestMapping(value = "/{tenantUuid}/{companyUuid}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<CompanyValue> deleteCompanyDetails(@PathVariable String tenantUuid,
            @PathVariable String companyUuid) {
        CompanyValue companyValue = null;
        try {
            companyValue = companyService.deleteCompany(tenantUuid,companyUuid);
        } catch (Exception e) {
            Logger.error("Error while deleting Seller:", e);
        }
        return new ResponseEntity<CompanyValue>(companyValue, HttpStatus.ACCEPTED);
    }

    @GetMapping (value = "/{tenantUuid}")
    public ResponseEntity<List<CompanyValue>> getAllCompanies(@PathVariable String tenantUuid) {
        List<CompanyValue> companyValues = null;
        try {
            companyValues = companyService.getAllCompany(tenantUuid);
            Logger.info("records " + companyValues.size());
        } catch (Exception e) {
            Logger.error("Error while getting Seller:", e);
        }
        return new ResponseEntity<>(companyValues, HttpStatus.ACCEPTED);
    }
}
