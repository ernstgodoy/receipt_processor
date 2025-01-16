package com.receipt_processor.ReceiptProcessor.models;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.receipt_processor.ReceiptProcessor.validations.ValidLocalDate;
import com.receipt_processor.ReceiptProcessor.validations.ValidLocalTime;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Receipt {
    @NotNull
    @Pattern(regexp = "^[\\w\\s\\-&]+$")
    String retailer;

    @NotNull
    @ValidLocalDate
    String purchaseDate;

    @NotNull
    @ValidLocalTime
    String purchaseTime;

    @NotNull
    @Pattern(regexp = "^\\d+\\.\\d{2}$")
    private String total;

    @NotNull
    @Size(min = 1)
    @Valid
    List<PurchaseItem> items;

    public Double getTotalAsDouble() {
        return Double.parseDouble(total);
    }

    public LocalDate getPurchaseDateAsLocalDate() {
        return LocalDate.parse(purchaseDate);
    }

    public LocalTime getPurchaseTimeAsLocaLTime() {
        return LocalTime.parse(purchaseTime);
    }
}
