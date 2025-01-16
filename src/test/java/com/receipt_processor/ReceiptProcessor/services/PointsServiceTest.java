package com.receipt_processor.ReceiptProcessor.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import com.receipt_processor.ReceiptProcessor.models.PurchaseItem;
import com.receipt_processor.ReceiptProcessor.models.Receipt;

public class PointsServiceTest {
    @InjectMocks
    private PointsService pointsService;
    
    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetPoints() {
        PurchaseItem item1 = new PurchaseItem();
        item1.setPrice(10.00);
        item1.setShortDescription("Short Description 1");
        
        PurchaseItem item2 = new PurchaseItem();
        item2.setPrice(5.25);
        item2.setShortDescription("Short Description2");

        Receipt receipt1 = new Receipt();
        receipt1.setTotal(item1.getPrice()); // round dollar amnt, multiple of .25: 75 pts
        receipt1.setPurchaseDate(LocalDate.of(2025, 01, 15)); // odd date: 6 pts
        receipt1.setPurchaseTime(LocalTime.of(14, 30, 0)); // between 2-4pm: 10 pts
        receipt1.setRetailer("Retailer 1"); // 9 alphanumeric: 9 pts
        receipt1.setItems(List.of(item1)); // no pairs, no multiples of 3: 0pts
        UUID uuid1 = UUID.randomUUID(); 
        int expectedPoints1 = 100;

        Receipt receipt2 = new Receipt();
        receipt2.setTotal(item1.getPrice() + item2.getPrice()); // not round dollar amnt, multiple of .25: 25 pts
        receipt2.setPurchaseDate(LocalDate.of(2025, 01, 14)); // even date: 0 pts
        receipt2.setPurchaseTime(LocalTime.of(12, 30, 0)); // not between 2-4pm: 0 pts
        receipt2.setRetailer("Retailer 02"); // 10 alphanumeric: 10 pts
        receipt2.setItems(List.of(item1, item2)); // one pairs, one multiples of 3: 7pts
        UUID uuid2 = UUID.randomUUID(); 
        int expectedPoints2 = 42;

        Integer points1 = pointsService.getPoints(uuid1, receipt1);
        Integer points2 = pointsService.getPoints(uuid2, receipt2);

        assertEquals(expectedPoints1, points1);
        assertEquals(expectedPoints2, points2);
    }
}
