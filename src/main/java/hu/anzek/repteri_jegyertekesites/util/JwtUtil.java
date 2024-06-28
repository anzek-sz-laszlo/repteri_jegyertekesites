/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hu.anzek.repteri_jegyertekesites.util;


import hu.anzek.repteri_jegyertekesites.model.Felhasznalo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


/**
 *
 * @author User
 */
@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;
    
    private SecretKey getSigningKey() {
        // byte[] keyBytes = Base64.getDecoder().decode(secret);
        // return Keys.hmacShaKeyFor(keyBytes);
        return Keys.hmacShaKeyFor(this.secret.getBytes());        
    }

    private String createToken(Map<String, Object> claims, String subject) {
        String jwt = Jwts.builder()
                         .setClaims(claims)
                         .setSubject(subject)
                         .setIssuedAt(new Date(System.currentTimeMillis()))
                         .setExpiration(new Date(System.currentTimeMillis() + this.expiration))
                         .signWith(this.getSigningKey(), SignatureAlgorithm.HS256)
                         .compact();
        System.out.println("JWT token = {" + jwt + "]");
        return jwt;
    }
    
    public String generateToken(Felhasznalo userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("kiegeszites", userDetails.getKiegeszites());
        claims.put("fix1", "progran fix1");
        claims.put("fix2", "program fix2");
        return this.createToken(claims, userDetails.getUsername());
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                   .setSigningKey(this.getSigningKey())
                   .build()
                   .parseClaimsJws(token)
                   .getBody();
    }
    
    //    @FunctionalInterface
    //    public interface Function<T, R> {
    //        R apply(T t);
    //    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = this.extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    
    public Date extractExpiration(String token) {
        return this.extractClaim(token, Claims::getExpiration); // Claims.getExpiration(Clams claims){}
    }

    private Boolean isTokenExpired(String token) {
        return this.extractExpiration(token).before(new Date());
    }
    
    public String extractUsername(String token) {
        return this.extractClaim(token, Claims::getSubject); // Claims.getSubject(Clams claims){}
    }
    
    public Boolean validateToken(String token, Felhasznalo userDetails) {
        final String username = this.extractUsername(token);
        return ( (username.equals(userDetails.getUsername())) && ( ! isTokenExpired(token)) );
    }
}
