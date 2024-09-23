package com.izeno.demo.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

@Component
@Order(1)
public class LoggingFilter extends OncePerRequestFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingFilter.class);
    private static final String API_URL_PATTERN = "http://localhost:8080/api/";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        if (shouldLog(request)) {
            ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
            ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

            long startTime = System.currentTimeMillis();
            filterChain.doFilter(requestWrapper, responseWrapper);
            long timeTaken = System.currentTimeMillis() - startTime;

            String requestBody = getStringValue(requestWrapper.getContentAsByteArray(),
                    request.getCharacterEncoding());
            String responseBody = getStringValue(responseWrapper.getContentAsByteArray(),
                    response.getCharacterEncoding());

            LOGGER.info(
                    "\nREQUESTURI={}; \nMETHOD={}; \nREQUEST ={}; \nRESPONSE CODE={}; \nRESPONSE={}; \n\n",
                    request.getMethod(), request.getRequestURL(), requestBody, response.getStatus(), responseBody,
                    timeTaken);

            responseWrapper.copyBodyToResponse();
        } else {
            filterChain.doFilter(request, response);
        }
    }

    private boolean shouldLog(HttpServletRequest request) {
        String requestURL = request.getRequestURL().toString();
        return requestURL.startsWith(API_URL_PATTERN);
    }

    private String getStringValue(byte[] contentAsByteArray, String characterEncoding) {
        try {
            return new String(contentAsByteArray, 0, contentAsByteArray.length, characterEncoding);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }
}