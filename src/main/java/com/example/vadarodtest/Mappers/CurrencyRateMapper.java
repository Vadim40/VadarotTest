package com.example.vadarodtest.Mappers;

import com.example.vadarodtest.Models.CurrencyRate;
import com.example.vadarodtest.Models.DTOs.RateDTO;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
@Component
public class CurrencyRateMapper {
    public CurrencyRate mapRateDtoToCurrencyRate(RateDTO rateDTO){
        BigDecimal ratePerUnit = rateDTO.getCur_OfficialRate()
                .divide(BigDecimal.valueOf(rateDTO.getCur_Scale()), RoundingMode.HALF_UP);

        return CurrencyRate.builder()
                .id(rateDTO.getCur_ID())
                .rate(ratePerUnit)
                .currencyCode(rateDTO.getCur_Abbreviation())
                .date(rateDTO.getDate())
                .build();
    }
}
