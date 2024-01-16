package com.sowermate.tenantService.controllers;

import com.sowermate.tenantService.entities.minimal.UserAuthProjection;
import com.sowermate.tenantService.entities.value.UserValue;
import com.sowermate.tenantService.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {


    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<UserValue> createGlassType(@RequestBody UserValue userValue) {
        UserValue userValue1 = userService.createUser(userValue);
        if (userValue1 == null) {
            return new ResponseEntity<UserValue>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<UserValue>(userValue1, HttpStatus.CREATED);
    }

    @GetMapping("/{tenantUuid}/{glassTypeUuid}")
    public ResponseEntity<UserValue> getSingleGlassType(@PathVariable String tenantUuid, @PathVariable String glassTypeUuid) {
        UserValue userValue = userService.getUser(tenantUuid, glassTypeUuid);
        return new ResponseEntity<>(userValue, HttpStatus.ACCEPTED);
    }

    @RequestMapping(value = "/{tenantUuid}", method = RequestMethod.GET)
    public ResponseEntity<List<UserValue>> getAllGlassType(@PathVariable String tenantUuid) {
        List<UserValue> allGlassType = userService.getAllUser(tenantUuid);
        return new ResponseEntity<>(allGlassType, HttpStatus.ACCEPTED);
    }

    @RequestMapping(value = "/{tenantUuid}/{glassTypeUuid}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<UserValue> deleteGlassType(@PathVariable String tenantUuid, @PathVariable String glassTypeUuid) {
        UserValue userValue = userService.deleteUser(tenantUuid, glassTypeUuid);
        return new ResponseEntity<UserValue>(userValue, HttpStatus.ACCEPTED);
    }

    @RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<UserValue> editGlassType(@RequestBody UserValue userValue) throws Exception {
        UserValue userValue1 = userService.editUser(userValue);
        return new ResponseEntity<UserValue>(userValue1, HttpStatus.CREATED);
    }

    @GetMapping("/auth/{userName}/{password}")
    public ResponseEntity<UserAuthProjection> userAuthentication(@PathVariable String userName, @PathVariable String password) {
        UserAuthProjection userValue = userService.userAuthentication(userName, password);
        if(ObjectUtils.isEmpty(userValue)) {
            return new ResponseEntity<>(userValue, HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(userValue, HttpStatus.ACCEPTED);
    }

}
