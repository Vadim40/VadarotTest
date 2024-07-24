package com.example.vadarodtest.Models.DTOs;

import java.math.BigDecimal;
import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RateDTO {
    @JsonProperty("Cur_ID")
    private Long cur_ID;

    @JsonProperty("Date")
    private LocalDate date;

    @JsonProperty("Cur_Abbreviation")
    private String cur_Abbreviation;

    @JsonProperty("Cur_Scale")
    private Integer cur_Scale;

    @JsonProperty("Cur_Name")
    private String cur_Name;

    @JsonProperty("Cur_OfficialRate")
    private BigDecimal cur_OfficialRate;
}
