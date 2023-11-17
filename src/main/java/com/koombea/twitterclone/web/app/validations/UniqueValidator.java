package com.koombea.twitterclone.web.app.validations;

import com.koombea.twitterclone.web.app.models.entities.User;
import com.koombea.twitterclone.web.app.repositories.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.ignoreCase;

/*
 * @creator: Oswaldo Montes
 * @date: November 16, 2023
 *
 */
@Component
public class UniqueValidator implements ConstraintValidator<Unique, String> {
    @Autowired
    private UserRepository repository;

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
            String methodName = "existsBy" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
            Method method = repository.getClass().getMethod(methodName, String.class);
            return !((boolean) method.invoke(repository, value));
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | NullPointerException e) {
            return true;
        }
    }
}
