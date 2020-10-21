package com.qf.service;

import com.qf.pojo.User;

public interface UserService {
    int loginUser(String email,String password);

    int insertUser(User user,String psw_again);

    User findByEmail(String email);

    int updateUser(User user);
}
