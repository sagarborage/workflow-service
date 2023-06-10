package com.sowermate.tenantService.controllers;
import com.sowermate.tenantService.entities.value.ConfirmThroughValue;
import com.sowermate.tenantService.services.ConfirmThroughService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/confirmThrough")
public class ConfirmThroughController {
    @Autowired
    private ConfirmThroughService confirmThroughService;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ConfirmThroughValue> createConfirmThrough(@RequestBody ConfirmThroughValue confirmThroughValue) throws Exception {
        ConfirmThroughValue confirmThroughValue1=confirmThroughService.CreateConfirmThrough(confirmThroughValue);
        return new ResponseEntity<ConfirmThroughValue>( confirmThroughValue1, HttpStatus.CREATED);
    }
    @GetMapping("/{uuid}")
    public ResponseEntity<ConfirmThroughValue> getConfirmThrough(@PathVariable String uuid) throws Exception {
        ConfirmThroughValue confirmThroughValue=confirmThroughService.getConfirmThrough(uuid);
        return  new ResponseEntity<>(confirmThroughValue, HttpStatus.ACCEPTED);
    }

    @GetMapping
    public ResponseEntity<List<ConfirmThroughValue>> getAllConfirmThrough() throws Exception {
        List<ConfirmThroughValue> confirmThroughValues = confirmThroughService.getAllConfirmThrough();
        return new ResponseEntity<> (confirmThroughValues ,HttpStatus.ACCEPTED);
    }

    @RequestMapping(value = "/{uuid}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<ConfirmThroughValue> deleteConfirmThrough(@PathVariable String uuid) throws Exception {
        ConfirmThroughValue confirmThroughValue =confirmThroughService.deleteConfirmThrough(uuid);
        return new ResponseEntity<ConfirmThroughValue>(confirmThroughValue ,HttpStatus.ACCEPTED);
    }
    @RequestMapping(value = "/{uuid}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<ConfirmThroughValue> editConfirmThroughValue(@RequestBody ConfirmThroughValue confirmThroughValue) throws Exception{
        ConfirmThroughValue  confirmThroughValue1=confirmThroughService.editConfirmThrough(confirmThroughValue);
        return new ResponseEntity<ConfirmThroughValue> (confirmThroughValue1, HttpStatus.CREATED);
    }


}
