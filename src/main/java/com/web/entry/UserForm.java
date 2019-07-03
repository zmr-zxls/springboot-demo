package com.web.entry;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.web.validator.annotation.PasswordValidator;

import org.springframework.lang.Nullable;

/**
 * 用户表单模板类
 */
public class UserForm {
  @NotNull
  @NotEmpty(message = "用户名不能为空")
  @Size(min = 3, max = 16, message = "用户名长度3~16之间")
  private String username;

  @NotNull
  @NotEmpty(message = "用户密码不能为空")
  @PasswordValidator(level = 2, message = "密码复杂度不够")
  private String password;

  @Nullable
  @Email
  private String email;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }
  
}