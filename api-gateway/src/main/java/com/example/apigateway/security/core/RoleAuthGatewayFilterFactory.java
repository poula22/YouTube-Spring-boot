package com.example.apigateway.security.core;

import com.example.controller.exception.HttpRequestException;
import com.example.util.JwtUtil;
import com.example.bussiness.model.ApiResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class RoleAuthGatewayFilterFactory
        extends AbstractGatewayFilterFactory<RoleAuthGatewayFilterFactory.Config> {

    private final JwtUtil jwtUtil = new JwtUtil();
    public RoleAuthGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            final ServerHttpResponse response = exchange.getResponse();
            final ServerHttpRequest request = exchange.getRequest();
            try {
                final String token = getTokenFromRequest(request);
                jwtUtil.validateToken(token);
            } catch (HttpRequestException requestException) {
                return onError(response, requestException.getMessage());
            }
            return chain.filter(exchange);
        };
    }

    private String getTokenFromRequest(ServerHttpRequest request) throws HttpRequestException {
        if (isAuthMissing(request)) throw new HttpRequestException("token is missing", 401);
        return request.getHeaders().getOrEmpty("Authorization").get(0);
    }

    private boolean isAuthMissing(ServerHttpRequest request) {
        return !request.getHeaders().containsKey("Authorization");
    }

    private Mono<Void> onError(ServerHttpResponse response, String errorMessage) {
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        return writeResponseAsJson(errorMessage, response);
    }

    private Mono<Void> writeResponseAsJson(String errorMessage, ServerHttpResponse response) {
        ObjectMapper objectMapper = new ObjectMapper();
        byte[] errorBytes;
        try {
            errorBytes = objectMapper.writeValueAsBytes(new ApiResponse.Error(401, errorMessage));
            objectMapper.writeValueAsBytes(response.bufferFactory().wrap(errorBytes));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return response
                .writeWith(Mono.just(response.bufferFactory().wrap(errorBytes)))
                .doOnError(error -> response.setComplete());
    }

    @Data
    public static class Config {
        private String role;
    }
}