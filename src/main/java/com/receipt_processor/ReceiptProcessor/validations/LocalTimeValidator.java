package com.receipt_processor.ReceiptProcessor.validations;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class LocalTimeValidator implements ConstraintValidator<ValidLocalTime, String> {
    private static final String TIME_PATTERN = "HH:mm";
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern(TIME_PATTERN);

    @Override
    public boolean isValid(String time, ConstraintValidatorContext context) {
        if (time == null) {
            return false;
        }

        try {
            LocalTime.parse(time, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
