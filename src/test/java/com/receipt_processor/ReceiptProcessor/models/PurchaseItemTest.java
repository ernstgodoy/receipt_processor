package com.receipt_processor.ReceiptProcessor.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

public class PurchaseItemTest {
    private Validator validator;

    @BeforeEach
    void setup(){
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }


    @Test
    void testValidPurchaseItem() {
        PurchaseItem item = new PurchaseItem("Valid Description", "10.99");
        Set<ConstraintViolation<PurchaseItem>> violations = validator.validate(item);

        assertTrue(violations.isEmpty());
    }

    @Test
    void testInvalid_ShortDescription() {
        PurchaseItem item = new PurchaseItem("Invalid#Description", "10.99");

        Set<ConstraintViolation<PurchaseItem>> violations = validator.validate(item);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());

        assertEquals("must match \"^[\\w\\s\\-]+$\"", violations.iterator().next().getMessage());
    }

    @Test
    void testInvalid_Price() {
        PurchaseItem item = new PurchaseItem("Valid Description", "10.999");
        Set<ConstraintViolation<PurchaseItem>> violations = validator.validate(item);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("must match \"^\\d+\\.\\d{2}$\"", violations.iterator().next().getMessage());
    }

    @Test
    void testPriceAsDouble() {
        PurchaseItem item = new PurchaseItem("Valid Description", "10.99");

        assertEquals(10.99, item.getPriceAsDouble());
    }
}
