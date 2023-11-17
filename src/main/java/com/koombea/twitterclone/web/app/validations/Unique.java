package com.koombea.twitterclone.web.app.validations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
 * @creator: Oswaldo Montes
 * @date: November 16, 2023
 *
 */



@Constraint(validatedBy = UniqueValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Unique {
    String message() default "A record with this value exists in the database";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @NotEmpty
    String fieldName();

    boolean ignoreBlank() default true;

    boolean ignoreNull() default true;
}
