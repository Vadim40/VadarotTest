package com.example.vadarodtest.Integration;

import com.example.vadarodtest.Mappers.CurrencyRateMapper;
import com.example.vadarodtest.Models.CurrencyRate;
import com.example.vadarodtest.Models.DTOs.RequestCodeDate;
import com.example.vadarodtest.Models.DTOs.RequestDate;
import com.example.vadarodtest.Repositories.CurrencyRateRepository;
import com.example.vadarodtest.Services.CurrencyRateServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.time.LocalDate;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CurrencyRateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CurrencyRateRepository currencyRateRepository;

    @Autowired
    private CurrencyRateServiceImpl currencyRateService;

    @Autowired
    private CurrencyRateMapper currencyRateMapper;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        currencyRateRepository.deleteAll();
    }

    @Test
    public void loadCurrencyRates_Success() throws Exception {
        LocalDate testDate = LocalDate.of(2024, 7, 24);
        RequestDate requestDate = new RequestDate(testDate);


        mockMvc.perform(MockMvcRequestBuilders.post("/api/cur-rate/load")
                        .content(objectMapper.writeValueAsString(requestDate))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        CurrencyRate savedRate = currencyRateRepository.findAll().stream()
                .filter(rate -> rate.getDate().equals(testDate))
                .findFirst().orElse(null);

        Assertions.assertThat(savedRate.getDate()).isEqualTo(testDate);
    }

    @Test
    public void loadCurrencyRates_NotFound() throws Exception {
        LocalDate testDate = LocalDate.of(1815, 6, 18);
        RequestDate requestDate = new RequestDate(testDate);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/cur-rate/load")
                        .content(objectMapper.writeValueAsString(requestDate))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
    @Test
    public void loadCurrencyRates_BadRequest() throws Exception {
        LocalDate testDate = LocalDate.of(2077, 6, 18);
        RequestDate requestDate = new RequestDate(testDate);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/cur-rate/load")
                        .content(objectMapper.writeValueAsString(requestDate))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void getCurrencyRate_Success() throws Exception {
        LocalDate testDate = LocalDate.of(2024, 7, 24);
        RequestCodeDate requestCodeDate = new RequestCodeDate("USD", testDate);
        BigDecimal rate=new BigDecimal("2.2");
        CurrencyRate currencyRate = new CurrencyRate();
        String code="USD";
        currencyRate.setCurrencyCode(code);
        currencyRate.setDate(testDate);
        currencyRate.setRate(rate);
        currencyRateRepository.save(currencyRate);


        mockMvc.perform(MockMvcRequestBuilders.get("/api/cur-rate/rate")
                        .content(objectMapper.writeValueAsString(requestCodeDate))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.currencyCode").value(code))
                .andExpect(MockMvcResultMatchers.jsonPath("$.rate").value(rate));
    }

    @Test
    public void getCurrencyRate_BadRequest() throws Exception {
        LocalDate testDate = LocalDate.of(2026, 7, 24);
        RequestCodeDate requestCodeDate = new RequestCodeDate("USD", testDate);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/cur-rate/rate")
                        .content(objectMapper.writeValueAsString(requestCodeDate))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void getCurrencyRate_NotFound() throws Exception {
        LocalDate testDate = LocalDate.of(2024, 7, 24);
        RequestCodeDate requestCodeDate = new RequestCodeDate("USD", testDate);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/cur-rate/rate")
                        .content(objectMapper.writeValueAsString(requestCodeDate))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
