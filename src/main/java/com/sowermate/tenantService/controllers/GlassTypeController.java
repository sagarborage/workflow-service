package com.sowermate.tenantService.controllers;

import com.sowermate.tenantService.entities.value.GlassSpecificationValue;
import com.sowermate.tenantService.entities.value.GlassTypeValue;
import com.sowermate.tenantService.services.GlassTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping(value = "/glass-types", produces = MediaType.APPLICATION_JSON_VALUE)
public class GlassTypeController {


    @Autowired
    private GlassTypeService glassTypeService;

    @RequestMapping( method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<GlassTypeValue> createGlassType(@RequestBody GlassTypeValue glassTypeValue) throws Exception {
        GlassTypeValue glassTypeValue1=glassTypeService.createGlassType(glassTypeValue);
        if (glassTypeValue1 == null) {
            return new ResponseEntity<GlassTypeValue>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<GlassTypeValue>( glassTypeValue1, HttpStatus.CREATED);
    }

    @GetMapping("/{tenantUuid}/{glassTypeUuid}")
    public ResponseEntity<GlassTypeValue> getSingleGlassType(@PathVariable String tenantUuid, @PathVariable String glassTypeUuid) throws Exception {
        GlassTypeValue glassTypeValue=glassTypeService.getGlassType(tenantUuid, glassTypeUuid);
        return  new ResponseEntity<>(glassTypeValue, HttpStatus.ACCEPTED);
    }

    @RequestMapping(value = "/{tenantUuid}", method = RequestMethod.GET)
    public ResponseEntity<List<GlassTypeValue>> getAllGlassType(@PathVariable String tenantUuid) throws Exception {
        List<GlassTypeValue> allGlassType = glassTypeService.getAllGlassType(tenantUuid);
        return new ResponseEntity<> (allGlassType ,HttpStatus.ACCEPTED);
    }

    @RequestMapping(value = "/{tenantUuid}/{glassTypeUuid}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<GlassTypeValue> deleteGlassType(@PathVariable String tenantUuid, @PathVariable String glassTypeUuid) throws Exception {
        GlassTypeValue glassTypeValue =glassTypeService.deleteGlassType(tenantUuid, glassTypeUuid);
        return new ResponseEntity<GlassTypeValue>(glassTypeValue ,HttpStatus.ACCEPTED);
    }
    @RequestMapping( method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<GlassTypeValue> editGlassType(@RequestBody GlassTypeValue glassTypeValue) throws Exception{
        GlassTypeValue glassTypeValue1=glassTypeService.editGlassType(glassTypeValue);
        return new ResponseEntity<GlassTypeValue> (glassTypeValue1, HttpStatus.CREATED);
    }

}
