package com.hnv99.exam.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@SuppressWarnings("all")
@Component
@Slf4j
public class JwtUtil {

    @Resource
    private ObjectMapper objectMapper;

    /**
     * JWT secret key
     */
    @Value("${jwt.secret}")
    private String secret;

    /**
     * JWT expiration time
     */
    @Value("${jwt.expire}")
    private Long expire;

    /**
     * Create JWT
     *
     * @return JWT token
     */
    public String createJwt(String userInfo, List<String> authList) {
        Date issDate = new Date();//Issuance time
        Date expireDate = new Date(issDate.getTime() + expire);//Expiration time
        // Define header information
        Map<String, Object> headerClaims = new HashMap<>();
        headerClaims.put("alg", "HS256");//Algorithm
        headerClaims.put("typ", "JWT");//Type must be JWT
        return JWT.create().withHeader(headerClaims)
                .withIssuer("wj")//Issuer
                .withIssuedAt(issDate)//Issuance time
                .withExpiresAt(expireDate)//Expiration time
                .withClaim("userInfo", userInfo)//Custom claim
                .withClaim("authList", authList)
                .sign(Algorithm.HMAC256(secret));//Use HS256 as signature, SECRET as key
    }

    /**
     * Verify token
     *
     * @param token
     * @return true if token is valid, false otherwise
     */
    public boolean verifyToken(String token) {
        // Build JWT verifier
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret)).build();
        try {
            verifier.verify(token);
            return true;
        } catch (JWTVerificationException e) {
            log.error("Verification failed");
            return false;
        }
    }

    /**
     * Get user information based on token
     *
     * @param token
     * @return user information
     */
    public String getUser(String token) {
        // Build JWT verifier
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret)).build();
        try {
            DecodedJWT jwt = verifier.verify(token);
            return jwt.getClaim("userInfo").asString();
        } catch (JWTVerificationException e) {
            log.error("Failed to retrieve user");
            return null;
        }
    }

    /**
     * Get permissions based on token
     *
     * @param token
     * @return list of permissions
     */
    public List<String> getAuthList(String token) {
        // Build JWT verifier
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret)).build();
        try {
            DecodedJWT jwt = verifier.verify(token);
            return jwt.getClaim("authList").asList(String.class);
        } catch (JWTVerificationException e) {
            log.error("Failed to retrieve the list of permissions");
            return null;
        }
    }
}
