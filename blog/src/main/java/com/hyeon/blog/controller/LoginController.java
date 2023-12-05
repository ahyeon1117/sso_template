package com.hyeon.blog.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class LoginController {

  // @GetMapping("/oauth/tokens/{social}")
  // @ResponseBody
  // public String getUserTokenDto(
  //   @PathVariable(value = "social") String social,
  //   @RequestParam(value = "code") String code,
  //   @RequestParam(value = "state", required = false) String state
  // ) {
  //   // naverService.getAccessToken(code, state);
  //   return "Success";
  // }

  @GetMapping("/login")
  public String test(
    @RequestParam(value = "code", required = false) String code,
    @RequestParam(value = "state", required = false) String state
  ) {
    return "forward:/index.html";
  }

  @GetMapping("/test")
  public String test2(
    @RequestParam(value = "code", required = false) String code,
    @RequestParam(value = "state", required = false) String state
  ) {
    return "test";
  }
}
