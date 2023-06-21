package com.sowermate.tenantService.controllers;
import com.sowermate.tenantService.entities.value.ConfirmThroughValue;
import com.sowermate.tenantService.services.ConfirmThroughService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/confirm-through")
public class ConfirmThroughController {
    @Autowired
    private ConfirmThroughService confirmThroughService;

    @RequestMapping( method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ConfirmThroughValue> createConfirmThrough(@RequestBody ConfirmThroughValue confirmThroughValue) throws Exception {
        ConfirmThroughValue confirmThroughValue1=confirmThroughService.createConfirmThrough(confirmThroughValue);
        return new ResponseEntity<ConfirmThroughValue>( confirmThroughValue1, HttpStatus.CREATED);
    }
    @GetMapping("/{confirmThroughUuid}")
    public ResponseEntity<ConfirmThroughValue> getConfirmThrough(@PathVariable String confirmThroughUuid) throws Exception {
        ConfirmThroughValue confirmThroughValue=confirmThroughService.getConfirmThrough(confirmThroughUuid);
        return  new ResponseEntity<>(confirmThroughValue, HttpStatus.ACCEPTED);
    }

    @GetMapping
    public ResponseEntity<List<ConfirmThroughValue>> getAllConfirmThrough() throws Exception {
        List<ConfirmThroughValue> confirmThroughValues = confirmThroughService.getAllConfirmThrough();
        return new ResponseEntity<> (confirmThroughValues ,HttpStatus.ACCEPTED);
    }

    @RequestMapping(value = "/{confirmThroughUuid}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<ConfirmThroughValue> deleteConfirmThrough(@PathVariable String confirmThroughUuid) throws Exception {
        ConfirmThroughValue confirmThroughValue =confirmThroughService.deleteConfirmThrough(confirmThroughUuid);
        return new ResponseEntity<ConfirmThroughValue>(confirmThroughValue ,HttpStatus.ACCEPTED);
    }
    @RequestMapping( method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<ConfirmThroughValue> editConfirmThroughValue(@RequestBody ConfirmThroughValue confirmThroughValue) throws Exception{
        ConfirmThroughValue  confirmThroughValue1=confirmThroughService.editConfirmThrough(confirmThroughValue);
        return new ResponseEntity<ConfirmThroughValue> (confirmThroughValue1, HttpStatus.CREATED);
    }


}
