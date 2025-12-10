package com.sowermate.security.handlers;

import com.sowermate.security.config.AuthConstants;
import com.sowermate.security.dtos.AuthenticationFailureResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * The {@link SecurityExceptionHandlerAdvice} class.
 *
 * @author sborage
 */
@Order(1)
@RestControllerAdvice(basePackages = "com.sowermate.security")
public class SecurityExceptionHandlerAdvice {

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(InternalAuthenticationServiceException.class)
    public ResponseEntity<AuthenticationFailureResponse> handleIllegalArgumentException(InternalAuthenticationServiceException ex) {
        String errorMessage = ex.getMessage();
        AuthenticationFailureResponse failureResponse = new AuthenticationFailureResponse();
        failureResponse.setMessage(errorMessage);
        failureResponse.setTokenExpired(errorMessage.contains(AuthConstants.ERROR_MESSAGE_TOKEN_EXPIRED));
        return new ResponseEntity<>(failureResponse, HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(AuthenticationServiceException.class)
    public ResponseEntity<AuthenticationFailureResponse> handleMissingServletRequestParameterException(Exception ex) {
        String errorMessage = ex.getMessage();
        AuthenticationFailureResponse failureResponse = new AuthenticationFailureResponse();
        failureResponse.setMessage(errorMessage);
        failureResponse.setTokenExpired(errorMessage.contains(AuthConstants.ERROR_MESSAGE_TOKEN_EXPIRED));
        return new ResponseEntity<>(failureResponse, HttpStatus.BAD_REQUEST);
    }

}
