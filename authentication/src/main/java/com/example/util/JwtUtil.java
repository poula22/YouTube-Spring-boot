package com.example.util;

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
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Claims extractAllClaims(String token) {

        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token).getBody();
        }catch (Exception e) {
            throw e; // custom Error
        }
    }

    public Boolean validateToken(String token) {
        return !isTokenExpired(token);
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public JwtSecurityModel extractUser(String token) {
        String userStr = extractClaim(token,Claims::getSubject);
        try {
            JwtSecurityModel user = objectMapper.readValue(userStr,JwtSecurityModel.class);
            return user;
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