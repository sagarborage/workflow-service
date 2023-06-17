package com.sowermate.tenantService.controllers;

import com.sowermate.tenantService.entities.value.GlassSpecificationValue;
import com.sowermate.tenantService.services.GlassSpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/glass-specifications")
public class GlassSpecificationController {

    @Autowired
    private GlassSpecificationService  glassSpecificationService;

    @RequestMapping( method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<GlassSpecificationValue> createGlassSpecification(@RequestBody GlassSpecificationValue glassSpecificationValue) throws Exception {
        GlassSpecificationValue glassSpecificationValue1=glassSpecificationService.createGlassSpecification(glassSpecificationValue);
        return new ResponseEntity<GlassSpecificationValue>( glassSpecificationValue1, HttpStatus.CREATED);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<GlassSpecificationValue> getSingleGlassSpecification(@PathVariable String uuid) throws Exception {
        GlassSpecificationValue glassSpecificationValue=glassSpecificationService.getGlassSpecification(uuid);
        return  new ResponseEntity<>(glassSpecificationValue, HttpStatus.ACCEPTED);
    }
    @GetMapping
    public ResponseEntity<List<GlassSpecificationValue>> getAllGlassSpecification() throws Exception {
        List<GlassSpecificationValue> allGlassSpecificationValues = glassSpecificationService.getAllGlassSpecification();
        return new ResponseEntity<> (allGlassSpecificationValues ,HttpStatus.ACCEPTED);
    }
    @RequestMapping(value = "/{uuid}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<GlassSpecificationValue> deleteGlassSpecification(@PathVariable String uuid) throws Exception {
        GlassSpecificationValue glassSpecificationValue =glassSpecificationService.deleteGlassSpecification(uuid);
        return new ResponseEntity<GlassSpecificationValue>(glassSpecificationValue ,HttpStatus.ACCEPTED);
    }
    @RequestMapping( method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<GlassSpecificationValue> editGlassSpecification(@RequestBody GlassSpecificationValue glassSpecificationValue) throws Exception{
        GlassSpecificationValue glassSpecificationValue1=glassSpecificationService.editGlassSpecification(glassSpecificationValue);
        return new ResponseEntity<GlassSpecificationValue> (glassSpecificationValue1, HttpStatus.CREATED);
    }

}
