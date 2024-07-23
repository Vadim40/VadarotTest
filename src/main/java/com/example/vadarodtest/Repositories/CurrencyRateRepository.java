package com.example.vadarodtest.Repositories;

import com.example.vadarodtest.Models.CurrencyRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface CurrencyRateRepository extends JpaRepository<CurrencyRate, Long> {
    Optional<List<CurrencyRate>> findCurrencyRatesByDate(LocalDate date);
    Optional<CurrencyRate> findCurrencyRateByCurrencyCodeAndDate(String code, LocalDate date);
    boolean existsByDate(LocalDate date);
}
