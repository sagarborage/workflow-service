package com.sowermate.security.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sowermate.security.config.AuthConstants;
import com.sowermate.security.dtos.AuthenticationFailureResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import java.io.IOException;

public class AuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private final ObjectMapper objectMapper;
    private final CustomResponseHeaderWriter responseHeaderWriter;

    public AuthenticationFailureHandler(ObjectMapper objectMapper, CustomResponseHeaderWriter responseHeaderWriter) {
        this.objectMapper = objectMapper;
        this.responseHeaderWriter = responseHeaderWriter;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException {

        responseHeaderWriter.writeCommonHeaders(request, response);

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // Set HTTP status code

        String errorMessage = exception.getMessage();
        AuthenticationFailureResponse failureResponse = new AuthenticationFailureResponse();
        failureResponse.setMessage(errorMessage);
        failureResponse.setTokenExpired(errorMessage.contains(AuthConstants.ERROR_MESSAGE_TOKEN_EXPIRED));
        objectMapper.writeValue(response.getWriter(), failureResponse);
    }
}
