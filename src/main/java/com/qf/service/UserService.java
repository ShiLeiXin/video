package com.qf.service;

import com.qf.pojo.User;

import java.util.List;

public interface UserService {
    public List<User> findByEmailAndPsw(User user);
    public List<User> findAllUsers();

    public List<User> findByUserAccount(String userAccount);

    public void updateUser(User user);

    public List<User> findByEmail(User user);

    public Integer insertUser(User user);
}
