package com.example.apigateway.security.core;

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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
@Slf4j
@Component
public class RoleAuthGatewayFilterFactory
        extends AbstractGatewayFilterFactory<RoleAuthGatewayFilterFactory.Config> {

    private final JwtUtil jwtUtil;
    public RoleAuthGatewayFilterFactory() {
        super(Config.class);
        jwtUtil = new JwtUtil();
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            final ServerHttpRequest request = exchange.getRequest();
            final ServerHttpResponse response = exchange.getResponse();


//            if (request.getPath().contextPath().value().contains("/user/")) return chain.filter(exchange);

            if (isAuthMissing(request)) {
                return onError(response, "token is missing", HttpStatus.UNAUTHORIZED);
            }
            final String token = getTokenFromRequest(request);
            log.info(token);
            if (!jwtUtil.validateToken(token)) {
                return onError(response, "token is invalid", HttpStatus.UNAUTHORIZED);
            }
//            UserDetails userDetails = User.builder()
//                    .username(jwtUtil.extractUser(token))
//                    .password("")
//                    .build();
//
//            // Set auth details
//            var authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//            authentication.setDetails(new WebAuthenticationDetails(request.getRemoteAddress().getHostString(),"12332134515"));

            // Inform the security context about auth details
//            SecurityContextHolder.getContext().setAuthentication(authentication);
            return chain.filter(exchange);
        };
    }

    private String getTokenFromRequest(ServerHttpRequest request) {
        return request.getHeaders().getOrEmpty("Authorization").get(0);
    }

    private boolean isAuthMissing(ServerHttpRequest request) {
        return !request.getHeaders().containsKey("Authorization");
    }

    private Mono<Void> onError(ServerHttpResponse response, String errorMessage, HttpStatus httpStatus) {
        response.setStatusCode(httpStatus);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        return writeResponseAsJson(errorMessage, response);
    }

    private Mono<Void> writeResponseAsJson(String errorMessage, ServerHttpResponse response) {
        ObjectMapper objectMapper = new ObjectMapper();
        byte[] errorBytes;
        try {
            errorBytes = objectMapper.writeValueAsBytes(new ApiResponse.Error("401", errorMessage));
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
