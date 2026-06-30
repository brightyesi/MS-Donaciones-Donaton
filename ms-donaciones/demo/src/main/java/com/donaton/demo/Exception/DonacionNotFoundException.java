package com.donaton.demo.Exception;

public class DonacionNotFoundException extends RuntimeException{
    public DonacionNotFoundException(String message) {
        super(message);
    }
}
