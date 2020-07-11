package com.freenow.blogservice.service;

import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class ValidationService implements EmailValidation{
    public List<String> findInvalidEmailAddresses(List<String> emailIds) {
        return emailIds.stream()
                .filter(email -> !validate(email))
                .collect(toList());
    }
}
