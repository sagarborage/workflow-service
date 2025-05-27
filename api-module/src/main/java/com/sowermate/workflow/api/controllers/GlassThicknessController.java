package com.sowermate.workflow.api.controllers;

import com.sowermate.workflow.domain.entities.value.GlassThicknessValue;
import com.sowermate.workflow.service.services.GlassThicknessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
@RequestMapping(value = "/glass-thickness", produces = MediaType.APPLICATION_JSON_VALUE)
public class GlassThicknessController {
    @Autowired
    private GlassThicknessService glassThicknessService;

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<GlassThicknessValue> createGlassThickness(@RequestBody GlassThicknessValue glassThicknessValue) {
        GlassThicknessValue glassThicknessValue1 = glassThicknessService.createGlassThickness(glassThicknessValue);
        if (glassThicknessValue1 == null) {
            return new ResponseEntity<GlassThicknessValue>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<GlassThicknessValue>(glassThicknessValue1, HttpStatus.CREATED);
    }

    @GetMapping("/{tenantUuid}/{glassThicknessUuid}")
    public ResponseEntity<GlassThicknessValue> getSingleGlassThickness(@PathVariable String tenantUuid, @PathVariable String glassThicknessUuid) {
        GlassThicknessValue glassThicknessValue = glassThicknessService.getGlassThicknessNameById(tenantUuid, glassThicknessUuid);
        return new ResponseEntity<>(glassThicknessValue, HttpStatus.ACCEPTED);
    }

    @RequestMapping(value = "/{tenantUuid}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<GlassThicknessValue>> getAllGlassThickness(@PathVariable String tenantUuid) {
        List<GlassThicknessValue> allGlassThicknessValue = glassThicknessService.getAllGlassThickness(tenantUuid);
        return new ResponseEntity<>(allGlassThicknessValue, HttpStatus.ACCEPTED);
    }

    @RequestMapping(value = "/{tenantUuid}/{glassThicknessUuid}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<GlassThicknessValue> deleteGlassThickness(@PathVariable String tenantUuid, @PathVariable String glassThicknessUuid) {
        GlassThicknessValue glassThicknessValue = glassThicknessService.deleteGlassThickness(tenantUuid, glassThicknessUuid);
        return new ResponseEntity<GlassThicknessValue>(glassThicknessValue, HttpStatus.ACCEPTED);
    }

    @RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<GlassThicknessValue> editGlassThickness(@RequestBody GlassThicknessValue glassThicknessValue) throws Exception {
        GlassThicknessValue glassThicknessValue1 = glassThicknessService.editGlassThickness(glassThicknessValue);
        return new ResponseEntity<GlassThicknessValue>(glassThicknessValue1, HttpStatus.CREATED);
    }


}
