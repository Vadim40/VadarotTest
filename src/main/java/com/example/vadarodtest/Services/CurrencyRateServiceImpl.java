package com.example.vadarodtest.Services;

import com.example.vadarodtest.Exceptions.CurrencyRateNotFoundException;
import com.example.vadarodtest.Mappers.CurrencyRateMapper;
import com.example.vadarodtest.Models.CurrencyRate;
import com.example.vadarodtest.Models.DTOs.RateDTO;
import com.example.vadarodtest.Repositories.CurrencyRateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CurrencyRateServiceImpl implements CurrencyRateService {
    private final CurrencyRateRepository currencyRateRepository;
    private final RestTemplate restTemplate = new RestTemplate();
    private final CurrencyRateMapper currencyRateMapper;

    @Value("${nbrb.api.url}")
    private String apiUrl;

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
                .orElseThrow(() -> new CurrencyRateNotFoundException("No currency rates found for date: " + date));
    }

    @Override
    public boolean isCurrencyRatesLoadedForDate(LocalDate date) {
        return currencyRateRepository.existsByDate(date);
    }

    @Override
    @Transactional
    public void loadCurrencyRatesForDate(LocalDate date) {
        if (isCurrencyRatesLoadedForDate(date)) {
            List<RateDTO> rateDTOs = fetchCurrencyRates(date);
            validateCurrencyRates(rateDTOs, date);
            saveCurrencyRateDTOs(rateDTOs);
        }
    }

    private List<RateDTO> fetchCurrencyRates(LocalDate date) {
        String url = String.format("%s?ondate=%s&periodicity=0", apiUrl, date.toString());
        ResponseEntity<List<RateDTO>> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {}
        );
        return Optional.ofNullable(responseEntity.getBody())
                .orElseThrow(() -> new CurrencyRateNotFoundException("No currency rates found for date: " + date));
    }

    private void validateCurrencyRates(List<RateDTO> rateDTOs, LocalDate date) {
        if (rateDTOs.isEmpty() || !date.equals(rateDTOs.get(0).getDate())) {
            throw new CurrencyRateNotFoundException("No currency rates found for date: " + date);
        }
    }

    private void saveCurrencyRateDTOs(List<RateDTO> rateDTOs) {
        List<CurrencyRate> currencyRates = rateDTOs.stream()
                .map(currencyRateMapper::mapRateDtoToCurrencyRate)
                .collect(Collectors.toList());
        currencyRateRepository.saveAll(currencyRates);
    }

}
