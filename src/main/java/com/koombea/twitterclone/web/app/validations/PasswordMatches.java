package com.koombea.twitterclone.web.app.validations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
 * @creator: Oswaldo Montes
 * @date: November 15, 2023
 *
 */
@Constraint(validatedBy = PasswordMatchesValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface PasswordMatches {
    String message() default "The password does not match with password confirmation";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
