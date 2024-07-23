package com.example.vadarodtest.Models.DTOs;

import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;

import java.time.LocalDate;

@Data
public class RequestDate {
    @PastOrPresent
    private LocalDate date;
}
