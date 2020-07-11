package com.freenow.blogservice.service;

import org.apache.commons.validator.routines.EmailValidator;

public interface EmailValidation {
    default boolean validate(String emailAddress) {
        return EmailValidator.getInstance().isValid(emailAddress);
    }
}
