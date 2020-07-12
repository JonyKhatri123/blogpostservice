package com.freenow.blogservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class ValidationService implements EmailValidation{

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public List<String> findInvalidEmailAddresses(List<String> emailIds) {
        logger.debug("Validating Email Addresses.");
        return emailIds.stream()
                .filter(email -> !validate(email))
                .collect(toList());
    }
}
