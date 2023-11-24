/*
 * @creator: Oswaldo Montes
 * @date: November 16, 2023
 *
 */
package com.koombea.twitterclone.web.app.validations;

import com.koombea.twitterclone.web.app.services.ValidatorService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class ExistsValidator implements ConstraintValidator<Exists, String> {
    @Autowired
    private ValidatorService service;

    private String entityName;

    private String columnName;

    @Override
    public void initialize(Exists constraintAnnotation) {
        this.entityName = constraintAnnotation.entityName();
        this.columnName = constraintAnnotation.columnName();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        try {
            return service.existsBy(entityName, columnName, value);
        } catch (NullPointerException exception) {
            return true;
        }
    }
}
