package com.example.vadarodtest.Controllers;

import com.example.vadarodtest.Mappers.CurrencyRateMapper;
import com.example.vadarodtest.Models.CurrencyRate;
import com.example.vadarodtest.Models.DTOs.CurrencyRateDTO;
import com.example.vadarodtest.Models.DTOs.RequestCodeDate;
import com.example.vadarodtest.Models.DTOs.RequestDate;
import com.example.vadarodtest.Services.CurrencyRateServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cur-rate")
@RequiredArgsConstructor
public class CurrencyRateController {

    private final CurrencyRateServiceImpl currencyRateService;
    private final CurrencyRateMapper currencyRateMapper;

    @Operation(summary = "Load currency rates for a specific date")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Data successfully loaded",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content)
    })
    @PostMapping("/load")
    public ResponseEntity<Object> loadCurrencyRates(@Valid @RequestBody RequestDate requestDate) {
        currencyRateService.loadCurrencyRatesForDate(requestDate.getDate());
        String response = "Data successfully loaded for date: " + requestDate.getDate();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Get currency rate by code and date")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Currency rate found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CurrencyRateDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Currency rate not found",
                    content = @Content)
    })
    @GetMapping("/rate")
    public ResponseEntity<Object> getCurrencyRate(@Valid @RequestBody RequestCodeDate codeDate) {
        CurrencyRate currencyRate = currencyRateService.findCurrencyRateByCurrencyCodeAndDate(
                codeDate.getCode(), codeDate.getDate());
        CurrencyRateDTO currencyRateDTO = currencyRateMapper.mapCurrencyRateToCurrencyRateDTO(currencyRate);
        return new ResponseEntity<>(currencyRateDTO, HttpStatus.OK);
    }
}
