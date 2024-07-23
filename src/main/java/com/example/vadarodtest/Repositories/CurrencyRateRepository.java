package com.example.vadarodtest.Repositories;

import com.example.vadarodtest.Models.CurrencyRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CurrencyRateRepository extends JpaRepository<CurrencyRate, Long> {
    List<CurrencyRate> findCurrencyRatesByDate(LocalDate date);
    CurrencyRate findCurrencyRateByCurrencyCodeAndDate(String code, LocalDate date);
}
