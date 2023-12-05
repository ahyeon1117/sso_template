package com.hyeon.blog.entity;

import com.hyeon.blog.enums.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(
  of = { "id", "oAuth2Id", "email", "nickname", "introduction", "role" }
)
public class UserInfo {

  @Id
  @GeneratedValue
  @Column(name = "user_id")
  private Long id;

  @Column(unique = true, nullable = false)
  private String oAuth2Id;

  @Column(unique = true)
  private String email;

  @Column(unique = true, nullable = false)
  private String nickname;

  private String profileImageUrl;

  private String introduction;

  @Column(nullable = false)
  @Enumerated(value = EnumType.STRING)
  private Role role;
}
