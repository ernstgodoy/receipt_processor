package com.receipt_processor.ReceiptProcessor.services;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.receipt_processor.ReceiptProcessor.models.PurchaseItem;
import com.receipt_processor.ReceiptProcessor.models.Receipt;

@Service
public class PointsService {
    Map<UUID, Integer> pointsByReceiptId = new HashMap<>();

    int getPoints(UUID uuid, Receipt receipt) {
        Integer points = pointsByReceiptId.get(uuid);

        if (points == null) {
            points = calculatePoints(uuid, receipt);
            pointsByReceiptId.put(uuid, points);
        }

        return points;
    }

    private int calculatePoints(UUID uuid,Receipt receipt){
        int points = 0;

        // One point for every alphanumeric character in the retailer name.
        points += calculateAlphanumericPoints(receipt.getRetailer());

        // 50 points if the total is a round dollar amount with no cents.
        if (isRoundDollar(receipt.getTotal())) {
            points += 50;
        }

        // 25 points if the total is a multiple of 0.25.
        if (isMultipleOfQuarter(receipt.getTotal())) {
            points += 25;
        }

        // 5 points for every two items on the receipt.
        points += calculateItemPoints(receipt.getItems());

        // If the trimmed length of the item description is a multiple of 3, 
        // multiply the price by 0.2 and round up to the nearest integer. 
        // The result is the number of points earned.
        points += calculateItemDescriptionPoints(receipt.getItems());

        // 6 points if the day in the purchase date is odd.
        if (receipt.getPurchaseDate().getDayOfMonth() % 2 != 0) {
            points += 6;
        }

        // 10 points if the time of purchase is after 2:00pm and before 4:00pm.
        if (isTimeBetween2And4PM(receipt.getPurchaseTime())) {
            points += 10;
        }

        return points; 
    }

    private static int calculateAlphanumericPoints(String retailer) {
        String cleanedString = retailer.replaceAll("[^a-zA-Z0-9]", "");
        return cleanedString.length();
    }

    private static boolean isRoundDollar(double total) {
        return total == Math.floor(total);
    }

    private static boolean isMultipleOfQuarter(double total) {
        return total % 0.25 == 0;
    }

    private static int calculateItemPoints(List<PurchaseItem> items) {
        return (items.size() / 2) * 5;
    }

    private static int calculateItemDescriptionPoints(List<PurchaseItem> items) {
        int points = 0;
        for (PurchaseItem item : items) {
            int descriptionLength = item.getShortDescription().trim().length();
            if (descriptionLength % 3 == 0) {
                points += Math.ceil(item.getPrice() * 0.2);
            }
        }
        return points;
    }

    private static boolean isTimeBetween2And4PM(LocalTime time) {
        return time.isAfter(LocalTime.of(14, 0)) && time.isBefore(LocalTime.of(16, 0));
    }
}
