package com.receipt_processor.ReceiptProcessor.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.receipt_processor.ReceiptProcessor.models.Receipt;

public class ReceiptServiceTest {
    @Mock
    private PointsService pointsService;
    
    @InjectMocks
    private ReceiptService receiptService;

    private Receipt mockReceipt;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        mockReceipt = new Receipt();
    }

    @Test 
    void testProcessReceipt() {
        String id = receiptService.processReceipt(mockReceipt);

        assertNotNull(id);
        assertNotNull(receiptService.getReceiptById(id));
    }

    @Test
    void testGetPoints() {
        int mockPoints = 100;
        String id = receiptService.processReceipt(mockReceipt);

        when(pointsService.getPoints(id, mockReceipt)).thenReturn(mockPoints);

        int points = receiptService.getPointsForReceiptById(id);
        
        assertEquals(points, mockPoints);
        verify(pointsService, times(1)).getPoints(id, mockReceipt);
    }

    @Test
    void testGetPionts_ReceiptDoesntExist() {
        String id = UUID.randomUUID().toString();

        assertNull(receiptService.getPointsForReceiptById(id));
    }

    @Test
    void testGetReceiptById() {
        String id = receiptService.processReceipt(mockReceipt);

        assertNotNull(receiptService.getReceiptById(id));
    }

    @Test
    void testGetReceiptById_ReceiptDoesntExist() {
        String id = UUID.randomUUID().toString();

        assertNull(receiptService.getReceiptById(id));
    }

    @Test
    void testGetAllReceipts() {
        String id1 = receiptService.processReceipt(mockReceipt);
        String id2 = receiptService.processReceipt(mockReceipt);

        Map<String, Receipt> receipts = receiptService.getAllReceipts();

        assertEquals(2, receipts.size());
        assertNotNull(receipts.get(id1));
        assertNotNull(receipts.get(id2));
    }
}
