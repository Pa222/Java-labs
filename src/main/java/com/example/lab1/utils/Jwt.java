package com.example.lab1.utils;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Service
public class Jwt {
    private static final String secretKet = "Secret key ahaahahah";

    public String generateToken(String login) {
        Date date = Date.from(LocalDate.now().plusDays(15).atStartOfDay(ZoneId.systemDefault()).toInstant());
        return Jwts.builder()
                .setSubject(login)
                .setExpiration(date)
                .signWith(SignatureAlgorithm.HS512, secretKet)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKet).parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException expEx) {
        } catch (UnsupportedJwtException unsEx) {
        } catch (MalformedJwtException mjEx) {
        } catch (SignatureException sEx) {
        } catch (Exception e) {
        }
        return false;
    }

    public String getLoginFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(secretKet).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }
}