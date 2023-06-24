package com.sowermate.tenantService.controllers;

import com.sowermate.tenantService.entities.value.PiTypeValue;
import com.sowermate.tenantService.services.PiTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pi-types")
public class PiTypeController {

    @Autowired
    private PiTypeService piTypeService;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<PiTypeValue> createPiType(@RequestBody PiTypeValue piTypeValue) throws Exception {
        PiTypeValue piTypeValue1=piTypeService.createPiType(piTypeValue);
        return new ResponseEntity<PiTypeValue>( piTypeValue1, HttpStatus.CREATED);
    }
    @GetMapping("/{tenantUuid}/{piTypeUuid}")
    public ResponseEntity<PiTypeValue> getPiTypeValue(@PathVariable String tenantUuid,@PathVariable String piTypeUuid) throws Exception {
        PiTypeValue piTypeValue=piTypeService.getPiType(tenantUuid, piTypeUuid);
        return  new ResponseEntity<>(piTypeValue, HttpStatus.ACCEPTED);
    }

    @GetMapping("/{tenantUuid}")
    public ResponseEntity<List<PiTypeValue>> getAllPiType(@PathVariable String tenantUuid) throws Exception {
        List<PiTypeValue> piTypeValues = piTypeService.getAllPiType(tenantUuid);
        return new ResponseEntity<> (piTypeValues ,HttpStatus.ACCEPTED);
    }

    @RequestMapping(value = "/{tenantUuid}/{piTypeUuid}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<PiTypeValue> deletePiType(@PathVariable String tenantUuid,@PathVariable String piTypeUuid) throws Exception {
        PiTypeValue piTypeValue =piTypeService.deletePiType(tenantUuid,piTypeUuid);
        return new ResponseEntity<PiTypeValue>(piTypeValue ,HttpStatus.ACCEPTED);
    }
    @RequestMapping( method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<PiTypeValue> editPiType(@RequestBody PiTypeValue piTypeValue) throws Exception{
        PiTypeValue  piTypeValue1=piTypeService.editPiType(piTypeValue);
        return new ResponseEntity<PiTypeValue> (piTypeValue1, HttpStatus.CREATED);
    }
}
