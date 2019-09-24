package com.web.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "user")
// 去掉json空值
// @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL) 已过期
@JsonInclude(JsonInclude.Include.NON_NULL)
@SuppressWarnings("unused")
public class User {
    @Id
    private String id;
    private String username;
    private String password;
    private String phoneNumber;
    private String email;
    private int level;

    public User () {
    }
    
    public User(String userid) {
        this.id = userid;
	}

	public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    // public String getPassword() {
    //     return password;
    // }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
