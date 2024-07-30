package com.example.vadarodtest.Models.DTOs;

import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestDate {
    @PastOrPresent(message = "must contain a past date or today's date")
    private LocalDate date;
}
