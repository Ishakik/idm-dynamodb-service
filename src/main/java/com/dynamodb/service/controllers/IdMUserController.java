package com.dynamodb.service.controllers;

import com.dynamodb.service.models.IdMUser;
import com.dynamodb.service.models.IdMUserWithRole;
import com.dynamodb.service.models.Role;
import com.dynamodb.service.services.IdMUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
public class IdMUserController {

    @Autowired
    private IdMUserService idMUserService;

    @GetMapping("/v1/IdMUser")
    public ResponseEntity<IdMUserWithRole> getIdMUser(@RequestParam("accountId") String accountId) {
        final IdMUserWithRole idMUserWithRole = idMUserService.readIdMUser(accountId);
        return new ResponseEntity<>(idMUserWithRole, HttpStatus.OK);
    }

    @GetMapping("/v1/IdMUsers")
    public ResponseEntity<List<IdMUserWithRole>> getIdMUsers(@RequestParam("accountIds") String accountIds) {
        List<String> accountIdsList = Arrays.asList(accountIds.split(","));
        final List<IdMUserWithRole> idMUserWithRoles = idMUserService.readAllIdMUsers(accountIdsList);
        return new ResponseEntity<>(idMUserWithRoles, HttpStatus.OK);
    }

    @PostMapping("/v1/IdMUser")
    public ResponseEntity<IdMUser> createIdMUser(@RequestBody IdMUser idMUser) {
        final IdMUser createdIdMUser = idMUserService.createIdMUser(idMUser);
        return new ResponseEntity<IdMUser>(createdIdMUser, HttpStatus.CREATED);
    }

    @PostMapping("/v1/IdMUser/{accountId}/IdMRole")
    public ResponseEntity<IdMUserWithRole> createIdMUser(@PathVariable String accountId, @RequestBody Role role) {
        IdMUserWithRole idMUserWithRole = idMUserService.addIdMUserRoleToIdmUser(accountId, role.getRole());
        return new ResponseEntity<>(idMUserWithRole, HttpStatus.OK);
    }


    @PutMapping("/v1/IdMUser")
    public ResponseEntity<IdMUser> updateIdMUser(@RequestBody IdMUser idMUser) {
        final IdMUser updatedIdMUser = idMUserService.updateIdMUser(idMUser);
        return new ResponseEntity<>(updatedIdMUser, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/v1/IdMUser")
    public ResponseEntity<IdMUserWithRole> deleteIdMUser(@RequestParam("accountId") String accountId) {
        idMUserService.deleteIdMUser(accountId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
