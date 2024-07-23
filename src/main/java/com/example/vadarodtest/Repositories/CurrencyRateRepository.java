package com.example.vadarodtest.Repositories;

import com.example.vadarodtest.Models.CurrencyRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface CurrencyRateRepository extends JpaRepository<CurrencyRate, Long> {
    CurrencyRate findCurrencyRateByCurrencyCode(String code);
    CurrencyRate findCurrencyRateByCurrencyCodeAndDate(String code, LocalDate date);
}
