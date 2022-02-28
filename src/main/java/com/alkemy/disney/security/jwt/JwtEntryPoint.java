package com.alkemy.disney.security.jwt;

//Esta clase va a comprobar si hay un token ELSE respuesta 401 no autorizado.

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class JwtEntryPoint implements AuthenticationEntryPoint {

    private final static Logger logger = LoggerFactory.getLogger(JwtEntryPoint.class);

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException auth) throws IOException, ServletException {
        
        logger.error("Fail en el método commence");
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "no autorizado");
    }

}
