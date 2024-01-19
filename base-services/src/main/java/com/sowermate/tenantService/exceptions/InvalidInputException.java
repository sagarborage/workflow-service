package com.sowermate.tenantService.exceptions;

/**
 * The {@link InvalidInputException} class.
 *
 * @author sborage
 */
public class InvalidInputException extends RuntimeException{
    public InvalidInputException(String message) {
        super(message);
    }
}
