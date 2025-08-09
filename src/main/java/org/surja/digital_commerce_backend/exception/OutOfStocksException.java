package org.surja.digital_commerce_backend.exception;

public class OutOfStocksException extends RuntimeException {
    public OutOfStocksException(String message) {
        super(message);
    }
}
