package com.sowermate.workflow.api.controllers;

import com.sowermate.workflow.domain.entities.value.JbCreationValue;
import com.sowermate.workflow.service.services.JbCreationService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/jbCreation")
public class JbCreationController {

    @Autowired
    private JbCreationService jbCreationService;

    private static final org.slf4j.Logger Logger = LoggerFactory.getLogger(JbCreationController.class);

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<JbCreationValue> createJbCreation(@RequestBody JbCreationValue jbCreationValue) {
        JbCreationValue jbCreationValue1 = null;
//        try {
            jbCreationValue1 = jbCreationService.createJbCreation(jbCreationValue);
//        } catch (Exception e) {
//            Logger.error("Error while creating Seller:", e);
//        }
        return new ResponseEntity<JbCreationValue>(jbCreationValue1, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<JbCreationValue> updateJbCreation(@RequestBody JbCreationValue jbCreationValue) {
        JbCreationValue jbCreationValue1 = null;
        try {
            jbCreationValue1 = jbCreationService.updateJbCreation(jbCreationValue);
        } catch (Exception e) {
            Logger.error("Error while editing Seller:", e);
        }
        return new ResponseEntity<JbCreationValue>(jbCreationValue1, HttpStatus.CREATED);
    }

    @GetMapping("/{toughenBatchProcessUuid}/{jbCreationUuid}")
    public ResponseEntity<JbCreationValue> getJbCreation(@PathVariable String toughenBatchProcessUuid,
                                                         @PathVariable String jbCreationUuid) {
        JbCreationValue jbCreationValue = null;
        try {
            jbCreationValue = jbCreationService.getJbCreation(toughenBatchProcessUuid, jbCreationUuid);
        } catch (Exception e) {
            Logger.error("Error while getting Seller:", e);
        }
        return new ResponseEntity<>(jbCreationValue, HttpStatus.OK);
    }

    @GetMapping(value = "/{toughenBatchProcessUuid}")
    public ResponseEntity<List<JbCreationValue>> getAllJbCreation(@PathVariable String toughenBatchProcessUuid) {
        List<JbCreationValue> jbCreationValues = null;
//        try {
            jbCreationValues = jbCreationService.getAllJbCreation(toughenBatchProcessUuid);
//            Logger.info("records " + jbCreationValues.size());
//        } catch (Exception e) {
//            Logger.error("Error while getting Seller:", e);
//        }
        return new ResponseEntity<>(jbCreationValues, HttpStatus.OK);
    }

    @RequestMapping(value = "/{toughenBatchProcessUuid}/{jbCreationUuid}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<JbCreationValue> deleteJbCreation(@PathVariable String toughenBatchProcessUuid,
                                                            @PathVariable String jbCreationUuid) {
        JbCreationValue jbCreationValue = null;
        try {
            jbCreationValue = jbCreationService.deleteJbCreation(toughenBatchProcessUuid, jbCreationUuid);
        } catch (Exception e) {
            Logger.error("Error while deleting Seller:", e);
        }
        return new ResponseEntity<JbCreationValue>(jbCreationValue, HttpStatus.OK);
    }

}
