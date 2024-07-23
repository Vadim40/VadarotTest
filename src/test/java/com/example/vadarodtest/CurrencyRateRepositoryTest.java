package com.example.vadarodtest;

import com.example.vadarodtest.Models.CurrencyRate;
import com.example.vadarodtest.Repositories.CurrencyRateRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class CurrencyRateRepositoryTest {
    @Autowired
    private CurrencyRateRepository currencyRateRepository;

    @Test
    public void testFindCurrencyRatesByDate() {
        List<CurrencyRate> currencyRates = Arrays.asList(
                CurrencyRate.builder().date(LocalDate.of(2024, 7, 23)).build(),
                CurrencyRate.builder().date(LocalDate.of(2024, 7, 23)).build(),
                CurrencyRate.builder().date(LocalDate.of(2024, 7, 22)).build());
        currencyRateRepository.saveAll(currencyRates);

        List<CurrencyRate> foundCurrencyRates = currencyRateRepository
                .findCurrencyRatesByDate(LocalDate.of(2024, 7, 23));

        Assertions.assertThat(foundCurrencyRates.size()).isEqualTo(2);
    }

    @Test
    public void testFindCurrencyRateByCurrencyCodeAndDate() {
        BigDecimal rate1 = new BigDecimal("2.6");
        BigDecimal rate2 = new BigDecimal("2.7");
        List<CurrencyRate> currencyRates = Arrays.asList(
                CurrencyRate.builder()
                        .date(LocalDate.of(2024, 7, 23))
                        .currencyCode("Usd")
                        .rate(rate1)
                        .build(),
                CurrencyRate.builder()
                        .currencyCode("Usd")
                        .date(LocalDate.of(2024, 7, 22))
                        .rate(rate2)
                        .build());
        currencyRateRepository.saveAll(currencyRates);

        CurrencyRate foundCurrencyRate = currencyRateRepository
                .findCurrencyRateByCurrencyCodeAndDate("Usd", LocalDate.of(2024, 7, 23));

        Assertions.assertThat(foundCurrencyRate.getRate()).isEqualTo(rate1);
    }
}
