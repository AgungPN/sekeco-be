package com.cashier.system.skecobe.services;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@AllArgsConstructor
public class ValidationService {
    private Validator validator;

    public void validate(Object request) {
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(request);
        if (0 != constraintViolations.size()) {
            throw new ConstraintViolationException(constraintViolations);
        }
    }
}
