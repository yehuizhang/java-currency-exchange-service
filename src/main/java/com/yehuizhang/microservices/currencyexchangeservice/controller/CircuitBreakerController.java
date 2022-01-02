package com.yehuizhang.microservices.currencyexchangeservice.controller;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CircuitBreakerController {

    private final Logger logger = LoggerFactory.getLogger(CircuitBreakerController.class);

    @GetMapping("/sample-api")
    @Retry(name = "sample-api", fallbackMethod = "fallbackMethod")
    public String sampleApi() {
        logger.info("Sample API call received");
        ResponseEntity<String> responseEntity = new RestTemplate().getForEntity("http://localhost:7777/dummy", String.class);

        return responseEntity.getBody();
    }

    @GetMapping("/sample-circuit-breaker")
    @CircuitBreaker(name = "sample-api", fallbackMethod = "fallbackMethod")
    public String sampleCircuitBreakerApi() {
        logger.info("Sample API call received");
        ResponseEntity<String> responseEntity = new RestTemplate().getForEntity("http://localhost:7777/dummy", String.class);

        return responseEntity.getBody();
    }

    public String fallbackMethod(Exception ex) {
        return "fallback response";
    }
}
