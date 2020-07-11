package com.freenow.blogservice.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Validate Email addresses and other validations")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { ValidationService.class })
public class ValidationServiceTests {

    @Autowired
    private ValidationService validationService;

    @ParameterizedTest
    @MethodSource("listOfEmails")
    @DisplayName("List of invalid Email addresses")
    public void shouldFindInvalidEmails(List<String> emailIds){
        List<String> emails = validationService.findInvalidEmailAddresses(emailIds);
        assertAll(
                ()->assertTrue(!emails.isEmpty()),
                ()->assertEquals(emails.size(), 2),
                ()->assertEquals(emails.get(0), "#@#@#"),
                ()->assertEquals(emails.get(1), "abc")
        );
        assertTrue(!emails.isEmpty());

    }

    private static Stream<Arguments> listOfEmails(){
        return Stream.of(
                Arguments.of(Arrays.asList("#@#@#","abc","abc@xyz.com","jony@freenow.com")));
    }

}
