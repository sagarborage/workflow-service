package com.sowermate.tenantService.controllers;

import com.sowermate.tenantService.entities.value.GlassSpecificationValue;
import com.sowermate.tenantService.services.GlassSpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/glass-specifications", produces = MediaType.APPLICATION_JSON_VALUE)
public class GlassSpecificationController {

    @Autowired
    private GlassSpecificationService  glassSpecificationService;

    @RequestMapping( method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<GlassSpecificationValue> createGlassSpecification(@RequestBody GlassSpecificationValue glassSpecificationValue) throws Exception {
        GlassSpecificationValue glassSpecificationValue1=glassSpecificationService.createGlassSpecification(glassSpecificationValue);
        if (glassSpecificationValue1 == null) {
            return new ResponseEntity<GlassSpecificationValue>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<GlassSpecificationValue>( glassSpecificationValue1, HttpStatus.CREATED);
    }
    @GetMapping("/{tenantUuid}/{glassSpecificationUuid}")
    public ResponseEntity<GlassSpecificationValue> getSingleGlassSpecification(@PathVariable String tenantUuid, @PathVariable String  glassSpecificationUuid) throws Exception {
        GlassSpecificationValue glassSpecificationValue=glassSpecificationService.getGlassSpecification(tenantUuid, glassSpecificationUuid);
        return  new ResponseEntity<>(glassSpecificationValue, HttpStatus.ACCEPTED);
    }
    @GetMapping(value = "/{tenantUuid}")
    public ResponseEntity<List<GlassSpecificationValue>> getAllGlassSpecification(@PathVariable String tenantUuid) throws Exception {
        List<GlassSpecificationValue> allGlassSpecificationValues = glassSpecificationService.getAllGlassSpecification(tenantUuid);
        return new ResponseEntity<> (allGlassSpecificationValues ,HttpStatus.ACCEPTED);
    }
    @RequestMapping(value = "/{tenantUuid}/{glassSpecificationUuid}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<GlassSpecificationValue> deleteGlassSpecification(@PathVariable String tenantUuid, @PathVariable String glassSpecificationUuid) throws Exception {
        GlassSpecificationValue glassSpecificationValue =glassSpecificationService.deleteGlassSpecification(tenantUuid, glassSpecificationUuid);
        return new ResponseEntity<GlassSpecificationValue>(glassSpecificationValue ,HttpStatus.ACCEPTED);
    }
    @RequestMapping( method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<GlassSpecificationValue> editGlassSpecification(@RequestBody GlassSpecificationValue glassSpecificationValue) throws Exception{
        GlassSpecificationValue glassSpecificationValue1=glassSpecificationService.editGlassSpecification(glassSpecificationValue);
        return new ResponseEntity<GlassSpecificationValue> (glassSpecificationValue1, HttpStatus.CREATED);
    }
}
