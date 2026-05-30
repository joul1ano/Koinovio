package com.koinovio.management_service.exceptions;

public class ApartmentAlreadyOccupiedException extends RuntimeException {
    public ApartmentAlreadyOccupiedException(String message) {
        super(message);
    }
}
