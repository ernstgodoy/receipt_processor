package com.receipt_processor.ReceiptProcessor.services;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.receipt_processor.ReceiptProcessor.models.Receipt;

@Service
public class ReceiptService {
    Map<UUID, Receipt> receipts = new HashMap<>();

    @Autowired
    PointsService pointsService;

    public UUID processReceipt(Receipt receipt) {
        UUID uuid = UUID.randomUUID();
        receipts.put(uuid, receipt);
        return uuid;
    }

    public Integer getPointsForReceiptById(UUID uuid) {
        Receipt receipt = receipts.get(uuid);

        if (receipt == null) {
            return null;
        }

        return pointsService.getPoints(uuid, receipt);
    }

    public Map<UUID, Receipt> getAllReceipts() {
        return receipts;
    }

    public Receipt getReceiptById(UUID uuid) {
        return receipts.get(uuid);
    }
}
