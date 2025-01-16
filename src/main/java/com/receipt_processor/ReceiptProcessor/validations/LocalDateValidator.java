package com.receipt_processor.ReceiptProcessor.validations;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class LocalDateValidator implements ConstraintValidator<ValidLocalDate, String> {
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);

    @Override
    public boolean isValid(String date, ConstraintValidatorContext context) {
        if (date == null) {
            return false;
        }

        try {
            LocalDate localDate = LocalDate.parse(date, formatter);

            if (localDate.isAfter(LocalDate.now())) {
                return false;
            }

            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
