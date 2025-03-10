package com.example.vadarodtest.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "currency_rate")
@Builder
public class CurrencyRate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "currency_code")
    private String currencyCode;
    @Column(name = "date")
    private LocalDate date;
    @Column(name = "rate", precision = 10, scale = 6)
    private BigDecimal rate;

}
