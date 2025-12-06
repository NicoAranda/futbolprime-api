package com.futbolprime.futbolprime_api.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    // CLAVE SECRETA (debe ser larga, mínimo 32 bytes para HS256)
    private static final String SECRET_KEY = "S3guridadSuperFuerteParaJWT_FutbolPrime_2025_ClaveMuyLarga";

    // Validez del token = 24 horas
    private static final long EXPIRATION_MS = 24 * 60 * 60 * 1000;

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public String generarToken(String email, String rol) {
        return Jwts.builder()
                .setSubject(email)                 // subject = email
                .claim("rol", rol)                 // guardamos rol: ADMIN / CLIENTE
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_MS))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String extraerEmail(String token) {
        return getClaims(token).getSubject();
    }

    public String extraerRol(String token) {
        Object rol = getClaims(token).get("rol");
        return rol != null ? rol.toString() : null;
    }

    public boolean validarToken(String token) {
        try {
            Claims claims = getClaims(token);
            Date expiracion = claims.getExpiration();
            return expiracion.after(new Date());
        } catch (Exception e) {
            return false;
        }
    }
}
