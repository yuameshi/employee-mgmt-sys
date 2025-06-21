package com.example.employee.mgmt.system.service;

import com.example.employee.mgmt.system.entity.User;

public interface UserService {

    User login(String identity, String password);

    void updateLastLoginTime(Long userId);
}
