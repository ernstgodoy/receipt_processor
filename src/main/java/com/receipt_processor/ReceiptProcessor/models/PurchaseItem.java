package com.receipt_processor.ReceiptProcessor.models;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PurchaseItem {
    @NotNull
    @Pattern(regexp = "^[\\w\\s\\-]+$")
    String shortDescription;

    @NotNull
    @Pattern(regexp = "^\\d+\\.\\d{2}$")
    String price;

    public Double getPriceAsDouble() {
        return Double.parseDouble(price);
    }
}
