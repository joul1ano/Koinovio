package com.koinovio.management_service.exceptions;

public class InvalidExpenseSubmissionException extends RuntimeException {
    public InvalidExpenseSubmissionException(String message) {
        super(message);
    }
}
