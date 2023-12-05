package com.hyeon.blog.repository;

import com.hyeon.blog.entity.UserInfo;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<UserInfo, Long> {
  Optional<UserInfo> findByEmail(String email);
}
