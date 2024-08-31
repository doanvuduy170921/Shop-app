package com.example.shopapp.components;

import com.example.shopapp.exceptions.InvalidParamException;
import com.example.shopapp.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cglib.core.internal.Function;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Component
@RequiredArgsConstructor
public class JwtTokenUtil {

    @Value("${jwt.expiration}")
    private  long expiration;

    @Value("${jwt.secretKey}")
    private String secretKey;

    public String generateToken(User user) throws Exception{
        Map<String, Object> claims = new HashMap<>();
        //this.generateSecretKey();
        claims.put("phoneNumber", user.getPhoneNumber());
        try{
        String token = Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getPhoneNumber())
                .setExpiration(new Date(System.currentTimeMillis() + expiration*1000L))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
        return token;
        }catch (Exception e){
            throw new InvalidParamException("Can not create token, error: " + e.getMessage());
      //  return null;
        }
    }
    private Key getSignKey() {
        byte[] bytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(bytes);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    public <T> T extractClaim(String token, Function<Claims,T> claimsResolver) {
        final  Claims claims = this.extractAllClaims(token);
       return claimsResolver.apply(claims);
    }
    public boolean isTokenExpired(String token) {
        Date expirationDate = this.extractClaim(token,Claims::getExpiration);
        return expirationDate.before(new Date());
    }
    public String extractPhoneNumber(String token) {
        return extractClaim(token,Claims::getSubject);
    }
    public boolean validateToken(String token, UserDetails userDetails) {
        String phoneNumber = extractPhoneNumber(token);
        return (phoneNumber.equals(userDetails.getUsername())&& !isTokenExpired(token));

    }
}
