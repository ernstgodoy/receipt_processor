package com.receipt_processor.ReceiptProcessor.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.receipt_processor.ReceiptProcessor.models.Receipt;
import com.receipt_processor.ReceiptProcessor.services.ReceiptService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/receipts")
public class ReceiptsController {
    
    private final ReceiptService receiptProcesserService;

    public ReceiptsController(ReceiptService receiptProcesserService) {
        this.receiptProcesserService = receiptProcesserService;
    }

    @PostMapping("/process")
    public ResponseEntity<Map<String, UUID>> processReceipts(@RequestBody Receipt receipt) {
        Map<String, UUID> response = Map.of("id", receiptProcesserService.processReceipt(receipt));
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/{uuid}")
    public Receipt getReceipt(@PathVariable UUID uuid) {
        return receiptProcesserService.getReceiptById(uuid);
    }

    @GetMapping("/all")
    public Map<UUID, Receipt> getAllReceipts() {
        return receiptProcesserService.getAllReceipts();
    }

    @GetMapping("/{uuid}/points")
    public ResponseEntity<Map<String, Integer>> getReceiptPoints(@PathVariable UUID uuid) {
        Map<String,Integer> response = Map.of("points", receiptProcesserService.getPointsForReceiptById(uuid));
        return ResponseEntity.ok(response);
    }
}
