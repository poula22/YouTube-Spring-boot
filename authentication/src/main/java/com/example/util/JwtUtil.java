package com.example.util;

import com.example.controller.exception.HttpRequestException;
import com.example.security.model.JwtSecurityModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class JwtUtil {
    private final String secret = "eyJhbGciOiJIUzI1NiJ9.eyJSb2xlIjoiQWRtaW4iLCJJc3N1ZXIiOiJJc3N1ZXIiLCJVc2VybmFtZSI6IkphdmFJblVzZSIsImV4cCI6MTcyNDcwODAzNywiaWF0IjoxNzI0NzA4MDM3fQ.n2NsdJ0OtCtxYAGo-PvtBajVzjiuoX9eBNYAYzSDreI";
    private final Key key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final ObjectWriter ow = objectMapper.writer().withDefaultPrettyPrinter();
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) throws HttpRequestException {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Claims extractAllClaims(String token) throws HttpRequestException {

        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token).getBody();
        }catch (Exception e) {
            throw new HttpRequestException("token is invalid: Can't parse token",401);// custom Error
        }
    }

    public void validateToken(String token) throws HttpRequestException {
        final boolean isExpired = isTokenExpired(token);
        if (isExpired) throw new HttpRequestException("Token is invalid: Expired",401);
    }

    private Boolean isTokenExpired(String token) throws HttpRequestException {
        return extractExpiration(token).before(new Date());
    }

    public Date extractExpiration(String token) throws HttpRequestException {
        return extractClaim(token, Claims::getExpiration);
    }

    public JwtSecurityModel extractUser(String token) throws HttpRequestException {
        String userStr = extractClaim(token,Claims::getSubject);
        try {
            return objectMapper.readValue(userStr,JwtSecurityModel.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public String generateToken(JwtSecurityModel jwtSecurityModel) {
        Map<String, Object> claims = new HashMap<>();
        try {
            String userStr = ow.writeValueAsString(jwtSecurityModel);
            return createToken(claims, userStr);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(key)
                .compact();
    }
}