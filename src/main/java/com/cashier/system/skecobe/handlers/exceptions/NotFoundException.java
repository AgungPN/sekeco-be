package com.cashier.system.skecobe.handlers.exceptions;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String attribute) {
        super(attribute + " not found");
    }
}
