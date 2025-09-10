package com.Manager.task_manager.Security.Jwt;

import com.Manager.task_manager.Entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private User user;
    @Value("${app.secret-key}")
    private String SECRET_KEY;

    @Value("${app.expiration-time}")
    private long expirationTime;

    public String extractUsername(String token){
        return extractClaim(token , Claims :: getSubject);
    }
    public String extractEmail(String token) {
        return extractClaim(token, claims -> claims.get("email", String.class));
    }
    public <T> T extractClaim(String token, Function<Claims,T> claimsResorver){
        final Claims claims = extractAllClaims(token);
        return claimsResorver.apply(claims);
    }
    private Claims extractAllClaims(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    private Key getSignInKey(){
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }


//    public String generateToken(UserDetails userDetails){
//        return generateToken(new HashMap<> (), userDetails , user);
//    }
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();

        // si ton User implémente UserDetails, tu peux caster
        User user = (User) userDetails;

        claims.put("userId", user.getId());
        claims.put("email", user.getEmail());
        claims.put("username", user.getUsername());
        claims.put("role", user.getUserRole().name()); // ex: "ADMIN" ou "USER"

        return createToken(claims, user);
    }

    private String createToken(Map<String, Object> claims, UserDetails userDetails) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername()) // identifiant principal (email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

//    public String generateToken(
//            Map<String, Object> extraClaims,
//            UserDetails userDetails,
//            User user
//    ){
//        return Jwts
//                .builder()
//                .setClaims(extraClaims)
//                .setSubject(user.getUsername())
//                //.claim("email", userDetails.getEmail())
//                .claim("email",userDetails.getUsername())
//                .setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 *24))
//                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
//                .compact();
//    }
//    public boolean isValidToken(String token, UserDetails userDetails){
//        final String userName = extractUsername(token);
//        return (userName.equals(userDetails.getUsername())) && !isTokenExpired(token);
//    }
public boolean isValidToken(String token, UserDetails userDetails) {
    try {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    } catch (JwtException e) {
        // token invalide, expiré, ou corrompu
        return false;
    }
}

    public boolean isTokenExpired(String token){
        return extractEcperation(token).before(new Date());
    }

    public Date extractEcperation(String token){
        return  extractClaim(token, Claims ::getExpiration);
    }
}
