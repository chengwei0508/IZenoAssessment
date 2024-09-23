/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.izeno.demo.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.izeno.demo.dtos.BaseDTO;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 *
 * @author cw
 */
@Component
@Order(2)
public class RateLimitControlFilter implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(RateLimitControlFilter.class);

    @Value("${app.max-api-requests-per-minute}")
    private int maxApiRequestsPerMinute;

    @Value("${app.rate-limit-api-patterns}")
    private String rateLimitApiPatternsConfig;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        LOGGER.info("RateLimitControlFilter::doFilter");
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String clientIpAddress = getClientIp(req);
        String uri = req.getRequestURI();
        LOGGER.info("uri ==== [{}]", uri);

        // Apply rate limiting only to API endpoints defined in the configuration
        if (matchesApiPattern(uri)) {
            String apiIdentifier = clientIpAddress + ":" + uri;

            // check whether it up to 5 times, if yes, return http response 429 
            try {
                // Check if request limit is exceeded
                if (isRateLimitExceeded(apiIdentifier)) {
                    LOGGER.warn("Rate limit exceeded for IP [{}] and API [{}]", clientIpAddress, uri);
                    res.setContentType("application/json");
                    res.setStatus(429); // Too many request
                    BaseDTO dto = new BaseDTO();
                    dto.setResponse("429");
                    dto.setDesc("Too many requests for this API - please try again later.");
                    objectMapper.writeValue(res.getWriter(), dto);
                    return;  // Stop further processing
                }
            } catch (ExecutionException e) {
                LOGGER.error("Error checking rate limit", e);
                res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                BaseDTO dto = new BaseDTO();
                dto.setResponse("99");
                dto.setDesc("Too many requests for this API - please try again later.");
                objectMapper.writeValue(res.getWriter(), dto);
                return;
            }
        }
        chain.doFilter(request, response);
    }

    private boolean matchesApiPattern(String uri) {
        List<String> rateLimitApiPatterns = Arrays.asList(rateLimitApiPatternsConfig.split(","));
        return rateLimitApiPatterns.stream()
                .map(this::convertToRegex) // Convert patterns to regex
                .anyMatch(pattern -> Pattern.matches(pattern, uri));
    }

    private String convertToRegex(String pattern) {
        // Replace placeholder syntax (e.g., `{personId}`) with a regex for digits or any matching group
        return pattern.replaceAll("\\{[^/]+}", "\\\\d+");
    }

    private boolean isRateLimitExceeded(String apiIdentifier) throws ExecutionException {
        int requests = requestCountsPerIpAndApi.get(apiIdentifier);

        LOGGER.info("Requests for [{}]: {}", apiIdentifier, requests);
        if (requests >= maxApiRequestsPerMinute) {
            return true;  // Rate limit exceeded
        }

        // Increment request count
        requestCountsPerIpAndApi.put(apiIdentifier, requests + 1);
        return false;
    }

    private final LoadingCache<String, Integer> requestCountsPerIpAndApi = CacheBuilder.newBuilder()
            .expireAfterWrite(1, TimeUnit.MINUTES)
            .build(new CacheLoader<String, Integer>() {
                @Override
                public Integer load(String key) {
                    return 0;
                }
            });

    private String getClientIp(HttpServletRequest request) {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null) {
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0];  // In case of multiple proxies
    }

}
