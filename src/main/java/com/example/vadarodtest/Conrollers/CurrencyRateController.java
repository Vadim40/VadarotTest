package com.example.vadarodtest.Conrollers;

import com.example.vadarodtest.Models.DTOs.RequestCodeDate;
import com.example.vadarodtest.Models.DTOs.RequestDate;
import com.example.vadarodtest.Models.DTOs.ResponseValidationError;
import com.example.vadarodtest.Services.CurrencyRateServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cur-rate")
@RequiredArgsConstructor
public class CurrencyRateController {

    private final CurrencyRateServiceImpl currencyRateService;

    @PostMapping("/load")
    public ResponseEntity<Object> loadCurrencyRates(@Valid @RequestBody RequestDate date,
                                                    BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ResponseValidationError errorResponse = new ResponseValidationError(bindingResult);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/rate")
    public ResponseEntity<Object> getCurrencyRate(@Valid @RequestBody RequestCodeDate codeDate,
                                                  BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ResponseValidationError errorResponse = new ResponseValidationError(bindingResult);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }
}
