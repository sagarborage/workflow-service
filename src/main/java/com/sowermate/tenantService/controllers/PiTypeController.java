package com.sowermate.tenantService.controllers;

import com.sowermate.tenantService.entities.value.AdditionalChargesValue;
import com.sowermate.tenantService.entities.value.PiTypeValue;
import com.sowermate.tenantService.services.PiTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pi-types")
@CrossOrigin(origins = "http://localhost:3000")
public class PiTypeController {

    @Autowired
    private PiTypeService piTypeService;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<PiTypeValue> createPiType(@RequestBody PiTypeValue piTypeValue) {
        PiTypeValue piTypeValue1 = piTypeService.createPiType(piTypeValue);
        if (piTypeValue1 == null) {
            return new ResponseEntity<PiTypeValue>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<PiTypeValue>(piTypeValue1, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{tenantUuid}/{piTypeUuid}")
    public ResponseEntity<PiTypeValue> getPiTypeValue(@PathVariable String tenantUuid, @PathVariable String piTypeUuid) {
        PiTypeValue piTypeValue = piTypeService.getPiType(tenantUuid, piTypeUuid);
        return new ResponseEntity<>(piTypeValue, HttpStatus.ACCEPTED);
    }

    @GetMapping(value = "/{tenantUuid}")
    public ResponseEntity<List<PiTypeValue>> getAllPiType(@PathVariable String tenantUuid) {
        List<PiTypeValue> piTypeValues = piTypeService.getAllPiType(tenantUuid);
        return new ResponseEntity<>(piTypeValues, HttpStatus.ACCEPTED);
    }

    @RequestMapping(value = "/{tenantUuid}/{piTypeUuid}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<Integer> deletePiType(@PathVariable String tenantUuid, @PathVariable String piTypeUuid) {
        int deletePiType = 0;
        deletePiType = piTypeService.deletePiType(tenantUuid, piTypeUuid);
        return new ResponseEntity<Integer>(deletePiType, HttpStatus.ACCEPTED);
    }

    @RequestMapping(method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<PiTypeValue> editPiType(@RequestBody PiTypeValue piTypeValue) throws Exception {
        PiTypeValue piTypeValue1 = piTypeService.editPiType(piTypeValue);
        return new ResponseEntity<PiTypeValue>(piTypeValue1, HttpStatus.CREATED);
    }
}
