package com.sowermate.tenantService.controllers;
import com.sowermate.tenantService.entities.value.StatusValue;
import com.sowermate.tenantService.services.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/statuses")
public class StatusController {

    @Autowired
    private StatusService statusService;

    @RequestMapping( method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<StatusValue> createStatus(@RequestBody StatusValue statusValue) throws Exception {
        StatusValue statusValue1=statusService.createStatus(statusValue);
        return new ResponseEntity<StatusValue>( statusValue, HttpStatus.CREATED);
    }
    @GetMapping("/{uuid}")
    public ResponseEntity<StatusValue> getSingleStatus(@PathVariable String uuid) throws Exception {
        StatusValue statusValue=statusService.getStatus(uuid);
        return  new ResponseEntity<>(statusValue, HttpStatus.ACCEPTED);
    }

    @GetMapping
    public ResponseEntity<List<StatusValue>> getAllStatus() throws Exception {
        List<StatusValue> statusValues = statusService.getAllStatus();
        return new ResponseEntity<> (statusValues ,HttpStatus.ACCEPTED);
    }
    @RequestMapping(value = "/{statusId}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<StatusValue> deleteStatus(@PathVariable int statusId) throws Exception {
        StatusValue statusValue =statusService.deleteStatus(statusId);
        return new ResponseEntity<StatusValue>(statusValue ,HttpStatus.ACCEPTED);
    }
    @RequestMapping(value = "/{uuid}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<StatusValue> editStatus(@RequestBody StatusValue statusValue) throws Exception{
        StatusValue statusValue1=statusService.editStatus(statusValue);
        return new ResponseEntity<StatusValue> (statusValue1, HttpStatus.CREATED);
    }

}
