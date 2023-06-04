package com.sowermate.tenantService.controllers;

import com.sowermate.tenantService.entities.value.GlassTypeValue;
import com.sowermate.tenantService.services.GlassTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/glassType")
public class GlassTypeController {

    //create
    @Autowired
    private GlassTypeService glassTypeService;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<GlassTypeValue> createGlassType(@RequestBody GlassTypeValue glassTypeValue) throws Exception {
        GlassTypeValue glassTypeValue1=glassTypeService.createGlassType(glassTypeValue);
        return new ResponseEntity<GlassTypeValue>( glassTypeValue1, HttpStatus.CREATED);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<GlassTypeValue> getSingleGlassType(@PathVariable String uuid) throws Exception {
        GlassTypeValue glassTypeValue=glassTypeService.getGlassType(uuid);
        return  new ResponseEntity<>(glassTypeValue, HttpStatus.ACCEPTED);
    }

    @GetMapping
    public ResponseEntity<List<GlassTypeValue>> getAllGlassType() throws Exception {
        List<GlassTypeValue> allGlassType = glassTypeService.getAllGlassType();
        return new ResponseEntity<> (allGlassType ,HttpStatus.ACCEPTED);
    }

    @RequestMapping(value = "/{glassTypeId}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<GlassTypeValue> deleteGlassType(@PathVariable int glassTypeId) throws Exception {
        GlassTypeValue glassTypeValue =glassTypeService.deleteGlassType(glassTypeId);
        return new ResponseEntity<GlassTypeValue>(glassTypeValue ,HttpStatus.ACCEPTED);
    }
    @RequestMapping(value = "/{uuid}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<GlassTypeValue> editGlassType(@RequestBody GlassTypeValue glassTypeValue) throws Exception{
        GlassTypeValue glassTypeValue1=glassTypeService.editGlassType(glassTypeValue);
        return new ResponseEntity<GlassTypeValue> (glassTypeValue1, HttpStatus.CREATED);
    }

}
