package com.freenow.blogservice.controller;

import com.freenow.blogservice.service.TestValidationService;
import com.sun.istack.internal.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestValidationController {

    @Autowired
    private TestValidationService testValidationService;

    @GetMapping("/validation/{userName}")
    public ResponseEntity findInvalidEmailsForUserPost(
            @NotNull @PathVariable("userName") String userName){
           return new ResponseEntity<>(testValidationService.findInvalidEmails(userName), HttpStatus.OK);
    }

}
