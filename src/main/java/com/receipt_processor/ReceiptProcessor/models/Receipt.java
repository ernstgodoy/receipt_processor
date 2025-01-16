package com.receipt_processor.ReceiptProcessor.models;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

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
    LocalDate purchaseDate;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    LocalTime purchaseTime;

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
}
