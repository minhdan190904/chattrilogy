package org.example.chattrilogy.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.chattrilogy.domain.RestResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper mapper;
    private final AuthenticationEntryPoint delegate = new BearerTokenAuthenticationEntryPoint();

    public CustomAuthenticationEntryPoint(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        this.delegate.commence(request, response, authException);
        response.setContentType("application/json;charset=UTF-8");

        RestResponse<Object> res = new RestResponse<>();
        res.setStatusCode(HttpStatus.UNAUTHORIZED.value());
        res.setError(authException.getCause() != null ? authException.getCause().getMessage() : "Unauthorized");
        res.setMessage("Token không hợp lệ (hết hạn, không đúng định dạng, hoặc không được cung cấp)");

        mapper.writeValue(response.getWriter(), res);
    }
}
