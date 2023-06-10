package com.sowermate.tenantService.controllers;

import com.sowermate.tenantService.entities.value.PiTypeValue;
import com.sowermate.tenantService.services.PiTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/piType")
public class PiTypeController {

    @Autowired
    private PiTypeService piTypeService;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<PiTypeValue> createPiType(@RequestBody PiTypeValue piTypeValue) throws Exception {
        PiTypeValue piTypeValue1=piTypeService.CreatePiType(piTypeValue);
        return new ResponseEntity<PiTypeValue>( piTypeValue1, HttpStatus.CREATED);
    }
    @GetMapping("/{uuid}")
    public ResponseEntity<PiTypeValue> getPiTypeValue(@PathVariable String uuid) throws Exception {
        PiTypeValue piTypeValue=piTypeService.getPiType(uuid);
        return  new ResponseEntity<>(piTypeValue, HttpStatus.ACCEPTED);
    }

    @GetMapping
    public ResponseEntity<List<PiTypeValue>> getAllPiType() throws Exception {
        List<PiTypeValue> piTypeValues = piTypeService.getAllPiType();
        return new ResponseEntity<> (piTypeValues ,HttpStatus.ACCEPTED);
    }

    @RequestMapping(value = "/{uuid}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<PiTypeValue> deletePiType(@PathVariable String uuid) throws Exception {
        PiTypeValue piTypeValue =piTypeService.deletePiType(uuid);
        return new ResponseEntity<PiTypeValue>(piTypeValue ,HttpStatus.ACCEPTED);
    }
    @RequestMapping(value = "/{uuid}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<PiTypeValue> editPiType(@RequestBody PiTypeValue piTypeValue) throws Exception{
        PiTypeValue  piTypeValue1=piTypeService.editPiType(piTypeValue);
        return new ResponseEntity<PiTypeValue> (piTypeValue1, HttpStatus.CREATED);
    }
}
