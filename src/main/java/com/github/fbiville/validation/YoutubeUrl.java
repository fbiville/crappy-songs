package com.github.fbiville.validation;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Target(FIELD)
@Retention(RUNTIME)
@NotBlank
@Constraint(validatedBy = YoutubeUrlValidator.class)
public @interface YoutubeUrl {
    String message() default "Not a valid Youtube URL";

    /* mandatory */
    Class<?>[] groups() default {};

    /* mandatory */
    Class<? extends Payload>[] payload() default {};

    String value() default "";
}