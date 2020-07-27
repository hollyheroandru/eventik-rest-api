package com.egorhristoforov.eventikrestapi.configuration.JWT;

import com.egorhristoforov.eventikrestapi.models.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.net.InetAddress;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtTokenUtil implements Serializable {

    @Value("${server.port}")
    private int serverPort;

    @Value("${eventik.app.jwtSecret}")
    private String jwtSecret;

    @Value("${eventik.app.accessTokenExpiration}")
    private int jwtAccessExpirationSecs;

    @Value("${eventik.app.refreshTokenExpiration}")
    private int jwtRefreshExpirationSecs;

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public Long getUserIdFromToken(String token) {
        Claims claims = getAllClaimsFromToken(token);

        return ((Number) claims.get("userId")).longValue();
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
    }

    public Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public String generateAccessToken(User user) {
        String ip = InetAddress.getLoopbackAddress().getHostAddress();

        return doGenerateToken(user.getUsername(), user.getId(), ip + serverPort,
                new Date(System.currentTimeMillis() + jwtAccessExpirationSecs * 1000));
    }

    public String generateRefreshToken(User user) {
        String ip = InetAddress.getLoopbackAddress().getHostAddress();
        return doGenerateToken(user.getUsername(), user.getId(), ip + serverPort,
                new Date(System.currentTimeMillis() + jwtRefreshExpirationSecs * 1000));
    }

    private String doGenerateToken(String subject, Long id, String issuer, Date expiredAt) {

        Claims claims = Jwts.claims().setSubject(subject);
        claims.put("userId", id);
        //claims.put("scopes", Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN")));

        return Jwts.builder()
                .setClaims(claims)
                .setIssuer(issuer)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(expiredAt)
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

}
