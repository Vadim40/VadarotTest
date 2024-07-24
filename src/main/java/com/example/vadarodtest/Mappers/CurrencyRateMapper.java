package com.example.vadarodtest.Mappers;

import com.example.vadarodtest.Models.CurrencyRate;
import com.example.vadarodtest.Models.DTOs.CurrencyRateDTO;
import com.example.vadarodtest.Models.DTOs.RateDTO;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class CurrencyRateMapper {
    public CurrencyRate mapRateDtoToCurrencyRate(RateDTO rateDTO) {
        BigDecimal ratePerUnit = rateDTO.getCur_OfficialRate()
                .divide(BigDecimal.valueOf(rateDTO.getCur_Scale()), 5, RoundingMode.HALF_UP);
        return CurrencyRate.builder()
                .id(rateDTO.getCur_ID())
                .rate(ratePerUnit)
                .currencyCode(rateDTO.getCur_Abbreviation())
                .date(rateDTO.getDate())
                .build();
    }

    public CurrencyRateDTO mapCurrencyRateToCurrencyRateDTO(CurrencyRate currencyRate) {
        return CurrencyRateDTO.builder()
                .id(currencyRate.getId())
                .currencyCode(currencyRate.getCurrencyCode())
                .date(currencyRate.getDate())
                .rate(currencyRate.getRate())
                .build();
    }

    public CurrencyRate mapCurrencyRateDTOToCurrencyRate(CurrencyRateDTO currencyRateDTO) {

        return CurrencyRate.builder()
                .id(currencyRateDTO.getId())
                .currencyCode(currencyRateDTO.getCurrencyCode())
                .date(currencyRateDTO.getDate())
                .rate(currencyRateDTO.getRate())
                .build();
    }
}

