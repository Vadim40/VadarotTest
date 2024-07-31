package com.example.vadarodtest.Models.DTOs;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestDate {
    @Schema(description = "Must contain a past date or today's date", example = "2024-07-30")
    @PastOrPresent(message = "must contain a past date or today's date")
    private LocalDate date;
}
