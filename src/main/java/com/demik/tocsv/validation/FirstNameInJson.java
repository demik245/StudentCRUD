// src/main/java/com/demik/tocsv/validation/FirstNameInJson.java
package com.demik.tocsv.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = FirstNameInJsonValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface FirstNameInJson {
    String message() default "First name must be present in the JSON file";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}