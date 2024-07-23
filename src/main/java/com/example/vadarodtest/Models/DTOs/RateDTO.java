package com.example.vadarodtest.Models.DTOs;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class RateDTO {
    private Long Cur_ID;
    private LocalDate Date;
    private String Cur_Abbreviation;
    private int Cur_Scale;
    private String Cur_Name;
    private BigDecimal Cur_OfficialRate;
}
