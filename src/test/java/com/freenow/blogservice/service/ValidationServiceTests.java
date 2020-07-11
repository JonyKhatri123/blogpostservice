package com.freenow.blogservice.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Validate Email addresses and other validations")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { ValidationService.class })
public class ValidationServiceTests {

    @Autowired
    private ValidationService validationService;

    @Test
    @DisplayName("List of invalid Email addresses")
    public void shouldFindInvalidEmails(){
        List<String> emailIds= Arrays.asList("");
        List<String> emails = validationService.findInvalidEmailAddresses(emailIds);
        assertTrue(!emails.isEmpty());
    }
}
