package com.hyeon.blog.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtProvider {

  // private final RefreshTokenService tokenService;

  @Value("${jwt.secretKey}")
  private String secretKey = "secretKey";

  SecretKey key = Jwts.SIG.HS512.key().build();

  @PostConstruct
  protected void init() {
    key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
  }

  // 원래는 return GeneratedToken으로 해야하지만 email은 추 후 추가 예정이기 때문에 String임

  public JwtToken generateToken(String email, String role) {
    // refreshToken과 accessToken을 생성한다.
    String refreshToken = generateRefreshToken(email, role);
    String accessToken = generateAccessToken(email, role);

    return new JwtToken(accessToken, refreshToken);
    // 토큰을 Redis에 저장한다.
    // tokenService.saveTokenInfo(email, refreshToken, accessToken);
    // return new GeneratedToken(accessToken, refreshToken);
  }

  public String generateRefreshToken(String email, String role) {
    // 토큰의 유효 기간을 밀리초 단위로 설정.
    long refreshPeriod = 1000L * 60L * 60L * 24L * 14; // 2주

    // 새로운 클레임 객체를 생성하고, 이메일과 역할(권한)을 셋팅
    Map<String, String> claims = new HashMap<>();
    claims.put("email", email);
    claims.put("role", role);

    // 현재 시간과 날짜를 가져온다.
    Date now = new Date();

    return Jwts
      .builder()
      // Payload를 구성하는 속성들을 정의한다.
      .claims(claims)
      // 발행일자를 넣는다.
      .issuedAt(now)
      // 토큰의 만료일시를 설정한다.
      .expiration(new Date(now.getTime() + refreshPeriod))
      // 지정된 서명 알고리즘과 비밀 키를 사용하여 토큰을 서명한다.
      .signWith(key)
      .compact();
  }

  public String generateAccessToken(String email, String role) {
    long tokenPeriod = 1000L * 60L * 30L; // 30분
    // 새로운 클레임 객체를 생성하고, 이메일과 역할(권한)을 셋팅
    Map<String, String> claims = new HashMap<>();
    claims.put("email", email);
    claims.put("role", role);

    Date now = new Date();
    return Jwts
      .builder()
      //    Payload를 구성하는 속성들을 정의한다.
      .claims(claims)
      // 발행일자를 넣는다.
      .issuedAt(now)
      // 토큰의 만료일시를 설정한다.
      .expiration(new Date(now.getTime() + tokenPeriod))
      // 지정된 서명 알고리즘과 비밀 키를 사용하여 토큰을 서명한다.
      .signWith(key)
      .compact();
  }

  public boolean verifyToken(String token) {
    try {
      Jws<Claims> claims = Jwts
        .parser()
        .verifyWith(key) // 비밀키를 설정하여 파싱한다.
        .build()
        .parseSignedClaims(token); // 주어진 토큰을 파싱하여 Claims 객체를 얻는다.
      // 토큰의 만료 시간과 현재 시간비교
      return claims.getPayload().getExpiration().after(new Date()); // 만료 시간이 현재 시간 이후인지 확인하여 유효성 검사 결과를 반환
    } catch (Exception e) {
      return false;
    }
  }

  // 토큰에서 Email을 추출한다.
  public String getUid(String token) {
    return Jwts
      .parser()
      .verifyWith(key)
      .build()
      .parseSignedClaims(token)
      .getPayload()
      .getSubject();
  }

  // 토큰에서 ROLE(권한)만 추출한다.
  public String getRole(String token) {
    return Jwts
      .parser()
      .verifyWith(key)
      .build()
      .parseSignedClaims(token)
      .getPayload()
      .getOrDefault("role", String.class)
      .toString();
  }
}
