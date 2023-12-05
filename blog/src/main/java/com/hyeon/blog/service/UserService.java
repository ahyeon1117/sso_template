package com.hyeon.blog.service;

import com.hyeon.blog.entity.UserInfo;
import com.hyeon.blog.repository.UsersRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Autowired
  private UsersRepository usersRepo;

  public Optional<UserInfo> findByEmail(String email) {
    return usersRepo.findByEmail(email);
  }
}
