package com.example.vadarodtest.Exceptions;

public class CurrencyRateNotFoundException extends RuntimeException{
    public CurrencyRateNotFoundException(String message){
        super(message);
    }
}
