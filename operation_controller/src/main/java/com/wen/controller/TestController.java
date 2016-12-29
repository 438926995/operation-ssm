package com.wen.controller;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

/**
 * @author huwenwen
 * @since 16/12/28
 */
@RestController
public class TestController {

  @Inject
  private Md5PasswordEncoder md5PasswordEncoder;

  @RequestMapping(value = "/test")
  public String test(){
    return md5PasswordEncoder.encodePassword("123456", "admin@qq.com");
  }
}
