package com.hyeon.blog.security.jwt;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtToken {

  private String accessToken;
  private String refreshToken;
}
