/*
 * @creator: Oswaldo Montes
 * @date: November 16, 2023
 *
 */
package com.koombea.twitterclone.web.app.validations;

import com.koombea.twitterclone.web.app.services.UserService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

public class UniqueValidator implements ConstraintValidator<Unique, String> {
    @Autowired
    private UserService userService;

    private String fieldName;

    private boolean ignoreBlank;

    private boolean ignoreNull;

    @Override
    public void initialize(Unique constraintAnnotation) {
        this.fieldName = constraintAnnotation.fieldName();
        this.ignoreBlank = constraintAnnotation.ignoreBlank();
        this.ignoreNull = constraintAnnotation.ignoreNull();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null && ignoreNull) return true;
        if (value.isBlank() && ignoreBlank) return true;

        try {
            return userService.existsBy(fieldName, value);
        } catch (NullPointerException exception) {
            return true;
        }
    }
}
