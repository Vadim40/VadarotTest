package com.example.vadarodtest.Integration;

import com.example.vadarodtest.Exceptions.CurrencyRateNotFoundException;
import com.example.vadarodtest.Models.CurrencyRate;
import com.example.vadarodtest.Repositories.CurrencyRateRepository;
import com.example.vadarodtest.Services.CurrencyRateServiceImpl;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class CurrencyRateServiceTest {
    @Autowired
    private CurrencyRateServiceImpl currencyRateService;

    @Autowired
    private CurrencyRateRepository currencyRateRepository;


    private final RestTemplate restTemplate=new RestTemplate();


    @Test
    @Transactional
    public void loadCurrencyRatesForDate_RealApiCall_Success() {
        LocalDate testDate = LocalDate.of(2024, 7, 24);


        currencyRateService.loadCurrencyRatesForDate(testDate);

        List<CurrencyRate> savedRates = currencyRateRepository.findAll();
        Assertions.assertThat(savedRates).isNotEmpty();

        CurrencyRate savedRate = savedRates.stream()
                .filter(rate -> rate.getCurrencyCode().equals("USD"))
                .findFirst().orElse(null);
        Assertions.assertThat(savedRate).isNotNull();
        Assertions.assertThat(savedRate.getRate()).isGreaterThan(BigDecimal.ZERO);

    }

    @Test
    public void loadCurrencyRatesForDate_RealApiCall_NoData() {
        LocalDate testDate = LocalDate.of(1815, 6, 18);

        try {
            currencyRateService.loadCurrencyRatesForDate(testDate);
        } catch (CurrencyRateNotFoundException e) {
            Assertions.assertThat(e.getMessage()).contains("No currency rates found for date: " + testDate);
        }
    }
}