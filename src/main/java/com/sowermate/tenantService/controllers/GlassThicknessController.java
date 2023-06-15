package com.sowermate.tenantService.controllers;
import com.sowermate.tenantService.entities.value.GlassThicknessValue;
import com.sowermate.tenantService.services.GlassThicknessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/glassthickness")
public class GlassThicknessController {
    @Autowired
    private GlassThicknessService glassThicknessService;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<GlassThicknessValue> createGlassThickness(@RequestBody GlassThicknessValue glassThicknessValue) throws Exception {
        GlassThicknessValue glassThicknessValue1=glassThicknessService.createGlassThickness(glassThicknessValue);
        return new ResponseEntity<GlassThicknessValue>( glassThicknessValue, HttpStatus.CREATED);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<GlassThicknessValue> getSingleGlassThickness(@PathVariable String uuid) throws Exception {
        GlassThicknessValue glassThicknessValue=glassThicknessService.getGlassThickness(uuid);
        return  new ResponseEntity<>(glassThicknessValue, HttpStatus.ACCEPTED);
    }

    @GetMapping
    public ResponseEntity<List<GlassThicknessValue>> getAllGlassThickness() throws Exception {
        List<GlassThicknessValue> allGlassThicknessValue = glassThicknessService.getAllGlassThickness();
        return new ResponseEntity<> (allGlassThicknessValue ,HttpStatus.ACCEPTED);
    }

    @RequestMapping(value = "/{glassThicknessId}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<GlassThicknessValue> deleteGlassThickness(@PathVariable int glassThicknessId) throws Exception {
        GlassThicknessValue glassThicknessValue =glassThicknessService.deleteGlassThickness(glassThicknessId);
        return new ResponseEntity<GlassThicknessValue>(glassThicknessValue ,HttpStatus.ACCEPTED);
    }
    @RequestMapping(value = "/{uuid}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<GlassThicknessValue> editGlassThickness(@RequestBody GlassThicknessValue glassThicknessValue) throws Exception{
        GlassThicknessValue glassThicknessValue1=glassThicknessService.editGlassThickness(glassThicknessValue);
        return new ResponseEntity<GlassThicknessValue> (glassThicknessValue1, HttpStatus.CREATED);
    }


}
