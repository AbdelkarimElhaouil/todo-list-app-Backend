package com.elhaouil.Todo_list_app.Jwt;

import com.elhaouil.Todo_list_app.Security.UserPrincipal;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {
    private final SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    public String generateJwt(String username){
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 60000 * 5))
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();
    }

    public boolean validateToken(String token, UserPrincipal user){
        String username = extractUsername(token);
        return username.equals(user.getUsername()) &&
                !extractExpirationDate(token).equals(new Date());
    }

    public Claims extractAllClaims(String token){
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

//    public <T> T extractClaim(String token, Function<Claims, T> claimResolver){
//       Claims claim = extractAllClaims(token);
//       return claimResolver.apply(claim);
//    }

    public String extractUsername(String token){
        return extractAllClaims(token)
                .getSubject();
    }

    public Date extractExpirationDate(String token){
        return extractAllClaims(token).getExpiration();
    }


}
