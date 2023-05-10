package com.bnpfortis.bnpfortis.exceptions;

public class EmptyBasketException extends RuntimeException {
    public EmptyBasketException(String message) {
        super(message);
    }
}
