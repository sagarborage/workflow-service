package com.sowermate.tenantService.controllers;

import com.sowermate.tenantService.entities.value.RoleTypeValue;
import com.sowermate.tenantService.services.RoleTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/role-types", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "http://localhost:3000")
public class RoleTypeController {


    @Autowired
    private RoleTypeService roleTypeService;

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<RoleTypeValue> createRoleType(@RequestBody RoleTypeValue roleTypeValue) {
        RoleTypeValue roleTypeValue1 = roleTypeService.createRoleType(roleTypeValue);
        if (roleTypeValue1 == null) {
            return new ResponseEntity<RoleTypeValue>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<RoleTypeValue>(roleTypeValue1, HttpStatus.CREATED);
    }

    @GetMapping("/{tenantUuid}/{roleTypeUuid}")
    public ResponseEntity<RoleTypeValue> getSingleRoleType(@PathVariable String tenantUuid, @PathVariable String roleTypeUuid) {
        RoleTypeValue roleTypeValue = roleTypeService.getRoleType(tenantUuid, roleTypeUuid);
        return new ResponseEntity<>(roleTypeValue, HttpStatus.ACCEPTED);
    }

    @RequestMapping(value = "/{tenantUuid}", method = RequestMethod.GET)
    public ResponseEntity<List<RoleTypeValue>> getAllRoleType(@PathVariable String tenantUuid) {
        List<RoleTypeValue> allRoleType = roleTypeService.getAllRoleType(tenantUuid);
        return new ResponseEntity<>(allRoleType, HttpStatus.ACCEPTED);
    }

    @RequestMapping(value = "/{tenantUuid}/{roleTypeUuid}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<RoleTypeValue> deleteRoleType(@PathVariable String tenantUuid, @PathVariable String roleTypeUuid) {
        RoleTypeValue roleTypeValue = roleTypeService.deleteRoleType(tenantUuid, roleTypeUuid);
        return new ResponseEntity<RoleTypeValue>(roleTypeValue, HttpStatus.ACCEPTED);
    }

    @RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<RoleTypeValue> editRoleType(@RequestBody RoleTypeValue roleTypeValue) throws Exception {
        RoleTypeValue roleTypeValue1 = roleTypeService.editRoleType(roleTypeValue);
        return new ResponseEntity<RoleTypeValue>(roleTypeValue1, HttpStatus.CREATED);
    }

}
