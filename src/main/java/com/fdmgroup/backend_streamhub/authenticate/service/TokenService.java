package com.fdmgroup.backend_streamhub.authenticate.service;


import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.stream.Collectors;

@Service
public class TokenService {

    private final JwtEncoder encoder;

    private String secretKey = "3cfa76ef14937c1c0ea519f8fc057a80fcd04a7420f8e8bcd0a7567c272e007b";

    public TokenService(JwtEncoder encoder) {
        this.encoder = encoder;
    }

    public String generateToken(Authentication authentication) {
        Instant now = Instant.now();
        String scope = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(2, ChronoUnit.HOURS))
                .subject(authentication.getName())
                .claim("scope", scope)
                .build();
        return this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    // The class below is used to generate a token to be used to authenticate users joining
    // a watch party
    public String generateToken(String partyCode) {
        Instant now = Instant.now();
        System.out.println("Current instant is: " + now);
        System.out.println("Date of instant is: " + Date.from(now));

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(2, ChronoUnit.HOURS))
                .subject(partyCode)
                .claim("role", "guest")
                .build();

         // String token = encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
//        String token = Jwts
//                .builder()
//                .setSubject(partyCode)
//                .setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis() + 3600000))
//                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
//                .compact();
        String token = Jwts
                .builder()
                .subject(partyCode)
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plus(2, ChronoUnit.HOURS)))
                .signWith(getSignInKey())
                .compact();

        return token;
    }

    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractPartyCode(String token) {
        Claims allClaims = extractAllClaims(token);
        String partyCode = allClaims.get("sub").toString();
        return partyCode;
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }


    public boolean isExpired(String token) {

        DecodedJWT decodedJWT = JWT.decode(token);

        // System.out.println(allClaims.get("exp").getClass());
        Date expiry = decodedJWT.getExpiresAt();

        if ( expiry.before(new Date(System.currentTimeMillis())) ) {
            return true;
        }
        return false;
    }

    public boolean isValidToken(String token, String partyCode) {
        // extract the party code from the token, and ensure that it matches the code of the
        // party that the user is trying to join, and that the token has not expired

        String extractedPartyCode = extractPartyCode(token);

        boolean expired = isExpired(token);

        return ( extractedPartyCode.equals(partyCode) && !expired );
    }

}
