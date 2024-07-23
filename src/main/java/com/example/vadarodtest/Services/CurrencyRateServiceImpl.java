package com.example.vadarodtest.Services;

import com.example.vadarodtest.Exceptions.CurrencyRateNotFoundException;
import com.example.vadarodtest.Models.CurrencyRate;
import com.example.vadarodtest.Repositories.CurrencyRateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CurrencyRateServiceImpl implements CurrencyRateService {
    private final CurrencyRateRepository currencyRateRepository;

    @Override
    public CurrencyRate findCurrencyRateById(Long id) {
        return currencyRateRepository.findById(id)
                .orElseThrow(() -> new CurrencyRateNotFoundException("CurrencyRate not found"));
    }

    @Override
    public CurrencyRate saveCurrencyRate(CurrencyRate currencyRate) {
        return currencyRateRepository.save(currencyRate);
    }

    @Override
    public CurrencyRate updateCurrencyRate(CurrencyRate currencyRate, Long id) {
        currencyRate.setId(id);
        return currencyRateRepository.save(currencyRate);
    }

    @Override
    public void deleteCurrencyRateById(Long id) {
        currencyRateRepository.deleteById(id);
    }

    @Override
    public CurrencyRate findCurrencyRateByCurrencyCodeAndDate(String code, LocalDate date) {
        return currencyRateRepository.findCurrencyRateByCurrencyCodeAndDate(code, date)
                .orElseThrow(() -> new CurrencyRateNotFoundException("CurrencyRate not found"));
    }

    @Override
    public List<CurrencyRate> findCurrencyRatesByDate(LocalDate date) {
        return currencyRateRepository.findCurrencyRatesByDate(date)
                .orElseThrow(() -> new CurrencyRateNotFoundException("CurrencyRates not founds for this data"));
    }

    @Override
    public boolean isCurrencyRatesLoadedForDate(LocalDate date) {
        return currencyRateRepository.existsByDate(date);
    }
}
