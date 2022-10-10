package com.zzbbc.spring.core.config.securities;

import java.util.Date;
import javax.crypto.SecretKey;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import com.zzbbc.spring.core.models.impl.UserDetailsImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {
    private static final Logger LOGGER = LogManager.getLogger(JwtTokenProvider.class);

    private final SecretKey SECRET_KEY;
    private final JwtParser JWT_PARSER;

    private long JWT_EXPIRATION;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public JwtTokenProvider(@Value("${jwt.secret}") String secret,
            @Value("${jwt.expiration}") String expiration) {
        this.SECRET_KEY = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
        this.JWT_PARSER = Jwts.parserBuilder().setSigningKey(SECRET_KEY).build();

        this.JWT_EXPIRATION = Long.parseLong(expiration);
    }

    // Tạo ra jwt từ thông tin user
    public String generateToken(UserDetailsImpl userDetails) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION);

        // Tạo chuỗi json web token từ id của user.
        return Jwts.builder().setSubject(userDetails.getUser().getUsername()).setIssuedAt(now)
                .setExpiration(expiryDate).signWith(SECRET_KEY, SignatureAlgorithm.HS512).compact();
    }

    // Lấy thông tin user từ jwt
    public String getUsernameFromJWT(String token) {
        Claims claims = JWT_PARSER.parseClaimsJws(token).getBody();

        return claims.getSubject();
    }

    public boolean validateToken(String authToken) {
        try {
            JWT_PARSER.parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException ex) {
            LOGGER.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            LOGGER.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            LOGGER.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            LOGGER.error("JWT claims string is empty.");
        }
        return false;
    }
}
