package com.projet3.demo.Services;
import com.projet3.demo.SpringSecurityConfig;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;


@Service
public class JWTService {

    private JwtEncoder jwtEncoder;
    private JwtDecoder jwtDecoder;

    public JWTService(JwtEncoder jwtEncoder, JwtDecoder jwtDecoder) {
        this.jwtEncoder = jwtEncoder;
        this.jwtDecoder = jwtDecoder;
    }

    public String generateToken(Authentication authentication) {
        Instant now = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(1, ChronoUnit.DAYS))
                .subject(authentication.getName())
                .build();
        JwtEncoderParameters jwtEncoderParameters = JwtEncoderParameters.from(JwsHeader.with(MacAlgorithm.HS256).build(), claims);
        return this.jwtEncoder.encode(jwtEncoderParameters).getTokenValue();
    }

    private final String secretKey = "9f86d081884c7d659a2feaa0c55ad015a3bf4f1b2b0b822cd15d6c15b0f00a08"; // Remplacez par votre clé secrète
    public boolean validateToken(String token) {
        try {
            Jwt jwt = jwtDecoder.decode(token);
            // Vous pouvez ajouter des validations supplémentaires ici si nécessaire
            return true; // Le token est valide
        } catch (JwtException e) {
            // Le token n'est pas valide
            return false;
        }
    }

    public JwtAuthenticationToken getContext(String token) {
        Jwt jwt = jwtDecoder.decode(token);
        return new JwtAuthenticationToken(jwt,new ArrayList<>());
    }
}
