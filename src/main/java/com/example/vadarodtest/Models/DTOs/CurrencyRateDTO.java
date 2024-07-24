package com.example.vadarodtest.Models.DTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CurrencyRateDTO {
    private Long id;
    private String currencyCode;
    private LocalDate date;
    private BigDecimal rate;
}
