package com.softserve.bot.exception;

public class SpecialtyNotFoundException extends RuntimeException{
    public SpecialtyNotFoundException() {
    }

    public SpecialtyNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

