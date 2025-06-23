package com.example.employee.mgmt.system.entity;

import java.util.Date;

public class User {

    private Long userId;
    private String username;
    private String password;
    private UserRole role;
    private Date lastLoginTime;

    public enum UserRole {
        ADMIN,
        USER,
    };

    public User() {
    }

    public User(Long userId, String username, String password, UserRole role, Date lastLoginTime) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.role = role;
        this.lastLoginTime = lastLoginTime;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long id) {
        this.userId = id;
    }

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

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date loginTime) {
        this.lastLoginTime = loginTime;
    }
}
