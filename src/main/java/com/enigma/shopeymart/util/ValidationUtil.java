package com.enigma.shopeymart.util;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class ValidationUtil {
    private final Validator validator;

    public void setValidator(Object object) {
        Set<ConstraintViolation<Object>> result = validator.validate(object);
        if (!result.isEmpty()) {
            throw new ConstraintViolationException(result);
        }
    }
}
