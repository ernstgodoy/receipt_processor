package com.receipt_processor.ReceiptProcessor.services;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.receipt_processor.ReceiptProcessor.models.Receipt;

@Service
public class ReceiptService {
    private static Map<String, Receipt> receipts = new HashMap<>();

    @Autowired
    PointsService pointsService;

    public String processReceipt(Receipt receipt) {
        String id = UUID.randomUUID().toString();
        receipts.put(id, receipt);
        return id;
    }

    public Integer getPointsForReceiptById(String id) {
        Receipt receipt = receipts.get(id);

        if (receipt == null) {
            return null;
        }

        return pointsService.getPoints(id, receipt);
    }

    public Map<String, Receipt> getAllReceipts() {
        return receipts;
    }

    public Receipt getReceiptById(String id) {
        return receipts.get(id);
    }
}
