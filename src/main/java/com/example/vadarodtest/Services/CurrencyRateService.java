package com.example.vadarodtest.Services;

import com.example.vadarodtest.Models.CurrencyRate;

import java.time.LocalDate;
import java.util.List;

public interface CurrencyRateService {
    CurrencyRate findCurrencyRateById(Long id);
    CurrencyRate saveCurrencyRate(CurrencyRate currencyRate);

    CurrencyRate updateCurrencyRate(CurrencyRate currencyRate, Long id);

    void deleteCurrencyRateById(Long id);

    CurrencyRate findCurrencyRateByCurrencyCodeAndDate(String code, LocalDate date);

    List<CurrencyRate> findCurrencyRatesByDate(LocalDate date);

    boolean isCurrencyRatesLoadedForDate(LocalDate date);
}
