package com.sowermate.tenantService.controllers;

import com.sowermate.tenantService.entities.value.JbCreationValue;
import com.sowermate.tenantService.services.JbCreationService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jbCreation")
public class JbCreationController {

    @Autowired
    private JbCreationService jbCreationService;

    private static final org.slf4j.Logger Logger= LoggerFactory.getLogger(JbCreationController.class);

    @RequestMapping( method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<JbCreationValue> createJbCreation(@RequestBody JbCreationValue jbCreationValue){
        JbCreationValue jbCreationValue1=null;
        try {
            jbCreationValue1=jbCreationService.createJbCreation(jbCreationValue);
        } catch (Exception e) {
            Logger.error("Error while creating Seller:", e);
        }
        return new ResponseEntity<JbCreationValue>(jbCreationValue1, HttpStatus.CREATED);
    }

    @RequestMapping( method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<JbCreationValue> updateJbCreation(@RequestBody JbCreationValue jbCreationValue){
        JbCreationValue jbCreationValue1=null;
        try {
            jbCreationValue1=  jbCreationService.updateJbCreation(jbCreationValue);
        } catch (Exception e) {
            Logger.error("Error while editing Seller:", e);
        }
        return new ResponseEntity< JbCreationValue>(jbCreationValue1,HttpStatus.CREATED);
    }

    @GetMapping("/{toughenBatchProcessUuid}/{jbCreationUuid}")
    public ResponseEntity<JbCreationValue> getJbCreation(@PathVariable String toughenBatchProcessUuid,
                                                          @PathVariable String jbCreationUuid) {
        JbCreationValue jbCreationValue = null;
        try {
            jbCreationValue = jbCreationService.getJbCreation(toughenBatchProcessUuid,jbCreationUuid);
        } catch (Exception e) {
            Logger.error("Error while getting Seller:", e);
        }
        return new ResponseEntity<>(jbCreationValue, HttpStatus.OK);
    }

    @GetMapping (value = "/{toughenBatchProcessUuid}")
    public ResponseEntity<List<JbCreationValue>> getAllJbCreation(@PathVariable String toughenBatchProcessUuid) {
        List<JbCreationValue> jbCreationValues = null;
        try {
            jbCreationValues = jbCreationService.getAllJbCreation(toughenBatchProcessUuid);
            Logger.info("records " + jbCreationValues.size());
        } catch (Exception e) {
            Logger.error("Error while getting Seller:", e);
        }
        return new ResponseEntity<>(jbCreationValues, HttpStatus.OK);
    }

    @RequestMapping(value = "/{toughenBatchProcessUuid}/{jbCreationUuid}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<JbCreationValue> deleteJbCreation(@PathVariable String toughenBatchProcessUuid,
                                                             @PathVariable String jbCreationUuid) {
        JbCreationValue jbCreationValue = null;
        try {
            jbCreationValue = jbCreationService.deleteJbCreation(toughenBatchProcessUuid,jbCreationUuid);
        } catch (Exception e) {
            Logger.error("Error while deleting Seller:", e);
        }
        return new ResponseEntity<JbCreationValue>(jbCreationValue, HttpStatus.OK);
    }

}
