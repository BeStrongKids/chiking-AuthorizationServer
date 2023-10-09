package com.bestrongkids.authorizationserver.filter;

import jakarta.servlet.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.csrf.CsrfToken;

import java.io.IOException;

public class CsrfTokenLoggerFilter implements Filter {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        Object o = request.getAttribute("_csrf");
        CsrfToken token = (CsrfToken) o;

        logger.info("CSRF token "+ token.getToken());

    }
}
