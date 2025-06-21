package com.example.employee.mgmt.system.service;

import com.example.employee.mgmt.system.dao.UserDao;
import com.example.employee.mgmt.system.entity.User;

public class UserServiceImpl implements UserService {

    UserDao userDao;

    public UserServiceImpl() {
        this.userDao = new UserDao();
    }

    public User login(String identity, String password) {
        return userDao.getByCredentials(identity, password);
    }

    public void updateLastLoginTime(Long userId) {
        userDao.updateLastLoginTime(userId);
    }
}
