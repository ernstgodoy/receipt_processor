package com.receipt_processor.ReceiptProcessor.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.receipt_processor.ReceiptProcessor.models.Receipt;
import com.receipt_processor.ReceiptProcessor.services.ReceiptService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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
    public ResponseEntity<Object> processReceipts(@RequestBody @Valid Receipt receipt, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The receipt is invalid.");
        }

        Map<String, String> response = Map.of("id", receiptProcesserService.processReceipt(receipt));
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/{id}")
    public Receipt getReceipt(@PathVariable String id) {
        return receiptProcesserService.getReceiptById(id);
    }

    @GetMapping("/all")
    public Map<String, Receipt> getAllReceipts() {
        return receiptProcesserService.getAllReceipts();
    }

    @GetMapping("/{id}/points")
    public ResponseEntity<Object> getReceiptPoints(@PathVariable String id) {
        Integer points = receiptProcesserService.getPointsForReceiptById(id);

        if (points == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No receipt found for that ID.");
        }

        Map<String,Integer> response = Map.of("points", points);
        return ResponseEntity.ok(response);
    }
}
