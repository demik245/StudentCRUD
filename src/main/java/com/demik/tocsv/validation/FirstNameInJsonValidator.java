// src/main/java/com/demik/tocsv/validation/FirstNameInJsonValidator.java
package com.demik.tocsv.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class FirstNameInJsonValidator implements ConstraintValidator<FirstNameInJson, String> {

    private Set<String> validFirstNames = new HashSet<>();

    @Override
    public void initialize(FirstNameInJson constraintAnnotation) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode rootNode = objectMapper.readTree(new File("src/main/resources/PolishNames/a.json"));
            for (JsonNode node : rootNode) {
                validFirstNames.add(node.get("name").asText());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isValid(String firstName, ConstraintValidatorContext context) {
        return validFirstNames.contains(firstName);
    }
}