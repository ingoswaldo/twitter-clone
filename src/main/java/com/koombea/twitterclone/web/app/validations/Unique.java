/*
 * @creator: Oswaldo Montes
 * @date: November 16, 2023
 *
 */
package com.koombea.twitterclone.web.app.validations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotEmpty;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = UniqueValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Unique {
    String message() default "A record with this value exists in the database";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @NotEmpty
    String entityName();

    @NotEmpty
    String columnName();
}
