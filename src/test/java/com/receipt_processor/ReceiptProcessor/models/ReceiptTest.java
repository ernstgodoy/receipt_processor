package com.receipt_processor.ReceiptProcessor.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

public class ReceiptTest {
        private Validator validator;

    @BeforeEach
    void setup(){
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void testValidReceipt() {
        PurchaseItem item = new PurchaseItem("Description", "10.00");
        Receipt receipt = new Receipt();
        receipt.setRetailer("Retailer");
        receipt.setPurchaseDate(LocalDate.of(2025, 01, 01).toString());
        receipt.setPurchaseTime(LocalTime.of(11, 30, 0).toString());
        receipt.setItems(List.of(item));
        receipt.setTotal("10.00");

        Set<ConstraintViolation<Receipt>> violations = validator.validate(receipt);

        assertTrue(violations.isEmpty());
    }

    @Test
    void testInvalid_Retailer() {
        PurchaseItem item = new PurchaseItem("Description", "10.00");
        Receipt receipt = new Receipt();
        receipt.setRetailer("Retailer###");
        receipt.setPurchaseDate(LocalDate.of(2025, 01, 01).toString());
        receipt.setPurchaseTime(LocalTime.of(11, 30, 0).toString());
        receipt.setItems(List.of(item));
        receipt.setTotal("10.00");

        Set<ConstraintViolation<Receipt>> violations = validator.validate(receipt);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("must match \"^[\\w\\s\\-&]+$\"", violations.iterator().next().getMessage());
    }

    @Test
    void testInvalid_NullPurchaseDate() {
        PurchaseItem item = new PurchaseItem("Description", "10.00");
        Receipt receipt = new Receipt();
        receipt.setRetailer("Retailer");
        receipt.setPurchaseTime(LocalTime.of(11, 30, 0).toString());
        receipt.setItems(List.of(item));
        receipt.setTotal("10.00");

        Set<ConstraintViolation<Receipt>> violations = validator.validate(receipt);

        assertFalse(violations.isEmpty());
        assertEquals(2, violations.size());
    }

    @Test
    void testInvalid_BadPurchaseDate() {
        PurchaseItem item = new PurchaseItem("Description", "10.00");
        Receipt receipt = new Receipt();
        receipt.setRetailer("Retailer");
        receipt.setPurchaseDate(LocalDate.now().plusYears(3).toString());
        receipt.setPurchaseTime(LocalTime.of(11, 30, 0).toString());
        receipt.setItems(List.of(item));
        receipt.setTotal("10.00");

        Set<ConstraintViolation<Receipt>> violations = validator.validate(receipt);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("Invalid date.", violations.iterator().next().getMessage());
    }

    @Test
    void testInvalid_NullPurchaseTime() {
        PurchaseItem item = new PurchaseItem("Description", "10.00");
        Receipt receipt = new Receipt();
        receipt.setRetailer("Retailer");
        receipt.setPurchaseDate(LocalDate.of(2025, 01, 01).toString());
        receipt.setItems(List.of(item));
        receipt.setTotal("10.00");

        Set<ConstraintViolation<Receipt>> violations = validator.validate(receipt);

        assertFalse(violations.isEmpty());
        assertEquals(2, violations.size());
    }

    @Test
    void testInvalid_BadPurchaseTime() {
        PurchaseItem item = new PurchaseItem("Description", "10.00");
        Receipt receipt = new Receipt();
        receipt.setRetailer("Retailer");
        receipt.setPurchaseDate(LocalDate.of(2025, 01, 01).toString());
        receipt.setPurchaseTime("30:20");
        receipt.setItems(List.of(item));
        receipt.setTotal("10.00");

        Set<ConstraintViolation<Receipt>> violations = validator.validate(receipt);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("Invalid time.", violations.iterator().next().getMessage());
    }

    @Test
    void testInvalid_Items() {
        PurchaseItem item = new PurchaseItem("Description", "10.000");
        Receipt receipt = new Receipt();
        receipt.setRetailer("Retailer");
        receipt.setPurchaseDate(LocalDate.of(2025, 01, 01).toString());
        receipt.setPurchaseTime(LocalTime.of(11, 30, 0).toString());
        receipt.setItems(List.of(item));
        receipt.setTotal("10.00");

        Set<ConstraintViolation<Receipt>> violations = validator.validate(receipt);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("must match \"^\\d+\\.\\d{2}$\"", violations.iterator().next().getMessage());
    }

    @Test
    void testInvalid_Total() {
        PurchaseItem item = new PurchaseItem("Description", "10.00");
        Receipt receipt = new Receipt();
        receipt.setRetailer("Retailer");
        receipt.setPurchaseDate(LocalDate.of(2025, 01, 01).toString());
        receipt.setPurchaseTime(LocalTime.of(11, 30, 0).toString());
        receipt.setItems(List.of(item));
        receipt.setTotal("10.000");

        Set<ConstraintViolation<Receipt>> violations = validator.validate(receipt);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("must match \"^\\d+\\.\\d{2}$\"", violations.iterator().next().getMessage());
    }

    @Test
    void testTotalAsDouble() {
        Receipt receipt = new Receipt();
        receipt.setTotal("10.00");

        assertEquals(10.00, receipt.getTotalAsDouble());
    }
}
