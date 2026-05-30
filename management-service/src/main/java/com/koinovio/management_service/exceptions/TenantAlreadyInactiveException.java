package com.koinovio.management_service.exceptions;

public class TenantAlreadyInactiveException extends RuntimeException {
    public TenantAlreadyInactiveException(String message) {
        super(message);
    }
}
