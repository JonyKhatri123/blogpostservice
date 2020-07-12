package com.freenow.blogservice.controller;

import com.freenow.blogservice.service.TestValidationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Api("Validate data by providing user name")
public class TestValidationController {

    @Autowired
    private TestValidationService testValidationService;

    @GetMapping("/validation/{userName}")
    @ApiOperation(value = "It validates email addresses of all comments on a post, posted by provided user.", consumes = MediaType.APPLICATION_JSON_VALUE, response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully fetched invalid Emails."),
            @ApiResponse(code = 400, message = "Failed to fetch invalid emails.")
    })
    public ResponseEntity findInvalidEmailsForUserPost(@PathVariable("userName") String userName) {
        if (userName.isEmpty()) {
            new ResponseEntity<>("Bad Request: append user name behind url", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(testValidationService.findInvalidEmails(userName), HttpStatus.OK);
    }

}
