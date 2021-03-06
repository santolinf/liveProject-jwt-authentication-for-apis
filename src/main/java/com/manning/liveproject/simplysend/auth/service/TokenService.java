package com.manning.liveproject.simplysend.auth.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.common.base.Strings;
import com.manning.liveproject.simplysend.auth.SecurityConstants;
import com.manning.liveproject.simplysend.auth.config.JwtProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.stream.Collectors;

import static com.manning.liveproject.simplysend.auth.SecurityConstants.TOKEN_AUTHORITY_CLAIM_NAME;

@RequiredArgsConstructor
public class TokenService {

    private final JwtProperties jwtProperties;

    public String generateToken(Authentication authentication) {
        Algorithm algorithm = jwtProperties.getAlgorithmOrDefault();
        return JWT.create()
                .withSubject(authentication.getName())
                .withClaim(TOKEN_AUTHORITY_CLAIM_NAME, authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .withIssuer(jwtProperties.getIssuer())
                .withExpiresAt(Date.from(Instant.now().plus(SecurityConstants.TOKEN_EXPIRY_TIME, ChronoUnit.MILLIS)))
                .sign(algorithm);
    }

    public boolean isNullOrEmptyOrNotTokenString(String token) {
        return Strings.isNullOrEmpty(token) || !token.startsWith(SecurityConstants.TOKEN_PREFIX);
    }

    public DecodedJWT verifyToken(String token) {
        return JWT.require(jwtProperties.getAlgorithmOrDefault())
                .build()
                .verify(token.replace(SecurityConstants.TOKEN_PREFIX, ""));
    }
}
