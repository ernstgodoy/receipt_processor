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
        UUID uuid = receiptService.processReceipt(mockReceipt);

        assertNotNull(uuid);
        assertNotNull(receiptService.getReceiptById(uuid));
    }

    @Test
    void testGetPoints() {
        int mockPoints = 100;
        UUID uuid = receiptService.processReceipt(mockReceipt);

        when(pointsService.getPoints(uuid, mockReceipt)).thenReturn(mockPoints);

        int points = receiptService.getPointsForReceiptById(uuid);
        
        assertEquals(points, mockPoints);
        verify(pointsService, times(1)).getPoints(uuid, mockReceipt);
    }

    @Test
    void testGetPionts_ReceiptDoesntExist() {
        UUID uuid = UUID.randomUUID();

        assertNull(receiptService.getPointsForReceiptById(uuid));
    }

    @Test
    void testGetReceiptById() {
        UUID uuid = receiptService.processReceipt(mockReceipt);

        assertNotNull(receiptService.getReceiptById(uuid));
    }

    @Test
    void testGetReceiptById_ReceiptDoesntExist() {
        UUID uuid = UUID.randomUUID();

        assertNull(receiptService.getReceiptById(uuid));
    }

    @Test
    void testGetAllReceipts() {
        UUID uuid1 = receiptService.processReceipt(mockReceipt);
        UUID uuid2 = receiptService.processReceipt(mockReceipt);

        Map<UUID, Receipt> receipts = receiptService.getAllReceipts();

        assertEquals(2, receipts.size());
        assertNotNull(receipts.get(uuid1));
        assertNotNull(receipts.get(uuid2));
    }
}
