package com.example.vadarodtest.Conrollers;

import com.example.vadarodtest.Mappers.CurrencyRateMapper;
import com.example.vadarodtest.Models.CurrencyRate;
import com.example.vadarodtest.Models.DTOs.CurrencyRateDTO;
import com.example.vadarodtest.Models.DTOs.RequestCodeDate;
import com.example.vadarodtest.Models.DTOs.RequestDate;
import com.example.vadarodtest.Services.CurrencyRateServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cur-rate")
@RequiredArgsConstructor
public class CurrencyRateController {

    private final CurrencyRateServiceImpl currencyRateService;
    private final CurrencyRateMapper currencyRateMapper;

    @PostMapping("/load")
    public ResponseEntity<Object> loadCurrencyRates(@Valid @RequestBody RequestDate requestDate) {
        currencyRateService.loadCurrencyRatesForDate(requestDate.getDate());
        String response = "Data successfully loaded for date: " + requestDate.getDate();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/rate")
    public ResponseEntity<Object> getCurrencyRate(@Valid @RequestBody RequestCodeDate codeDate) {
        CurrencyRate currencyRate = currencyRateService.findCurrencyRateByCurrencyCodeAndDate(
                codeDate.getCode(), codeDate.getDate());
        CurrencyRateDTO currencyRateDTO = currencyRateMapper.mapCurrencyRateToCurrencyRateDTO(currencyRate);
        return new ResponseEntity<>(currencyRateDTO,HttpStatus.OK);
    }

}