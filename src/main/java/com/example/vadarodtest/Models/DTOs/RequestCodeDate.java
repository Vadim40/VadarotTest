package com.example.vadarodtest.Models.DTOs;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestCodeDate {
    @NotNull
    @Length(min = 3, max = 3, message = "Currency code must have only 3 characters")
    private String code;
    @PastOrPresent(message = "must contain a past date or today's date")
    private LocalDate date;
}
