package com.example.vadarodtest.Models.DTOs;

import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(description = "Currency code must have only 3 characters", example = "USD")
    @NotNull
    @Length(min = 3, max = 3, message = "Currency code must have only 3 characters")
    private String code;

    @Schema(description = "Must contain a past date or today's date", example = "2024-07-30")
    @PastOrPresent(message = "must contain a past date or today's date")
    private LocalDate date;
}
