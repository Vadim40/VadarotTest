package com.example.vadarodtest.Conrollers;

import com.example.vadarodtest.Exceptions.CurrencyRateNotFoundException;
import com.example.vadarodtest.Mappers.CurrencyRateMapper;
import com.example.vadarodtest.Models.CurrencyRate;
import com.example.vadarodtest.Models.DTOs.CurrencyRateDTO;
import com.example.vadarodtest.Models.DTOs.RequestCodeDate;
import com.example.vadarodtest.Models.DTOs.RequestDate;
import com.example.vadarodtest.Models.DTOs.ResponseValidationError;
import com.example.vadarodtest.Services.CurrencyRateServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cur-rate")
@RequiredArgsConstructor
@Slf4j
public class CurrencyRateController {

    private final CurrencyRateServiceImpl currencyRateService;
    private final CurrencyRateMapper currencyRateMapper;

    @PostMapping("/load")
    public ResponseEntity<Object> loadCurrencyRates(@Valid @RequestBody RequestDate requestDate,
                                                    BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ResponseValidationError errorResponse = new ResponseValidationError(bindingResult);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
        boolean isDataLoadedForDate = currencyRateService.isCurrencyRatesLoadedForDate(requestDate.getDate());
        if (!isDataLoadedForDate) {
            try {
                currencyRateService.loadCurrencyRatesForDate(requestDate.getDate());
                log.info("Loaded currency rates for date: {}", requestDate.getDate());
            } catch (CurrencyRateNotFoundException e) {
                log.error("Currency rate not found  date: {}", requestDate.getDate());
                return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
            }

        }
        String response = "Data successfully loaded for date: " + requestDate.getDate();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/rate")
    public ResponseEntity<Object> getCurrencyRate(@Valid @RequestBody RequestCodeDate codeDate,
                                                  BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ResponseValidationError errorResponse = new ResponseValidationError(bindingResult);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
        try {
            CurrencyRate currencyRate = currencyRateService.findCurrencyRateByCurrencyCodeAndDate(
                    codeDate.getCode(), codeDate.getDate());
            CurrencyRateDTO currencyRateDTO = currencyRateMapper.mapCurrencyRateToCurrencyRateDTO(currencyRate);
            return new ResponseEntity<>(currencyRateDTO, HttpStatus.OK);
        } catch (CurrencyRateNotFoundException e) {
            log.error("Currency rate not found for code: {} and date: {}", codeDate.getCode(), codeDate.getDate(), e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

    }
}
