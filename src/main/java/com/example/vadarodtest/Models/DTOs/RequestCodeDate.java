package com.example.vadarodtest.Models.DTOs;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

@Data
public class RequestCodeDate {
    @NotNull
    @Length(min = 3, max = 3, message = "Currency code must have only 3 characters")
    private String code;
    @PastOrPresent
    private LocalDate date;
}
