package com.sowermate.tenantService.controllers;
import com.sowermate.tenantService.entities.value.GlassThicknessValue;
import com.sowermate.tenantService.services.GlassThicknessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/glass-thickness")
public class GlassThicknessController {
    @Autowired
    private GlassThicknessService glassThicknessService;

    @RequestMapping( method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<GlassThicknessValue> createGlassThickness(@RequestBody GlassThicknessValue glassThicknessValue) throws Exception {
        GlassThicknessValue glassThicknessValue1=glassThicknessService.createGlassThickness(glassThicknessValue);
        return new ResponseEntity<GlassThicknessValue>( glassThicknessValue, HttpStatus.CREATED);
    }

    @GetMapping("/{glassThicknessUuid}")
    public ResponseEntity<GlassThicknessValue> getSingleGlassThickness(@PathVariable String glassThicknessUuid) throws Exception {
        GlassThicknessValue glassThicknessValue=glassThicknessService.getGlassThickness(glassThicknessUuid);
        return  new ResponseEntity<>(glassThicknessValue, HttpStatus.ACCEPTED);
    }

    @GetMapping
    public ResponseEntity<List<GlassThicknessValue>> getAllGlassThickness() throws Exception {
        List<GlassThicknessValue> allGlassThicknessValue = glassThicknessService.getAllGlassThickness();
        return new ResponseEntity<> (allGlassThicknessValue ,HttpStatus.ACCEPTED);
    }

    @RequestMapping(value = "/{glassThicknessUuid}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<GlassThicknessValue> deleteGlassThickness(@PathVariable String glassThicknessUuid) throws Exception {
        GlassThicknessValue glassThicknessValue =glassThicknessService.deleteGlassThickness(glassThicknessUuid);
        return new ResponseEntity<GlassThicknessValue>(glassThicknessValue ,HttpStatus.ACCEPTED);
    }
    @RequestMapping( method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<GlassThicknessValue> editGlassThickness(@RequestBody GlassThicknessValue glassThicknessValue) throws Exception{
        GlassThicknessValue glassThicknessValue1=glassThicknessService.editGlassThickness(glassThicknessValue);
        return new ResponseEntity<GlassThicknessValue> (glassThicknessValue1, HttpStatus.CREATED);
    }


}
