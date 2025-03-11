package com.example.project.JWT;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
public class JWTUtil {

    @Value("${secret-key}") // NOTE 토큰 생성 키
    private String secretKey;

    @Value("${issuer}") // NOTE 토근 생성자
    private String issuer;

    @Value("${expiration-minutes}")
    private Long expirationMinutes;

    // NOTE 토큰 생성
    public String createToken(String username) {
        return Jwts.builder()
                .signWith(new SecretKeySpec(secretKey.getBytes(), SignatureAlgorithm.HS512.getJcaName())) // 키를 이용해 서명
                .setSubject(username) // 토큰의 사용자 이름 설정
                .setIssuer(issuer) // 토큰의 생성자 설정
                .setIssuedAt(Timestamp.valueOf(LocalDateTime.now())) // 토큰 발급 시간
                .setExpiration(Date.from(Instant.now().plus(expirationMinutes, ChronoUnit.MINUTES))) // 만료시간
                .compact();
    }

    // NOTE 토큰 검증
    public String validateToken(String token) {
        try{
            return Jwts.parserBuilder()
                    .setSigningKey(secretKey) // 서명 검증을 위한 키
                    .build()
                    .parseClaimsJws(token) // 토큰을 파싱해서 ClaimsJws객체로 변환
                    .getBody() // body 추출
                    .getSubject(); // 사용자 정보 반환
        }catch (Exception e){
            return null;
        }
    }

    // NOTE 토큰 갱신
    public String refreshToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey.getBytes()) // 서명 키를 설정
                    .build() // 객체 생성
                    .parseClaimsJws(token); // 토큰을 파싱해서 ClaimsJws객체로 변환
            return null;
        }catch (ExpiredJwtException e){
            // 만료되었을 때 사용자의 정보를 가져온다
            String subject = e.getClaims().getSubject();
            // 토큰을 새롭게 발급한다.
            return Jwts.builder()
                    .signWith(new SecretKeySpec(secretKey.getBytes(), SignatureAlgorithm.HS512.getJcaName()))
                    .setSubject(subject)
                    .setIssuer(issuer)
                    .setIssuedAt(Timestamp.valueOf(LocalDateTime.now()))
                    .setExpiration(Date.from(Instant.now().plus(expirationMinutes, ChronoUnit.MINUTES))) // 만료시간
                    .compact();
        }catch (JwtException e){
            // 나머지 에러 처리
            return null;
        }
    }

}
