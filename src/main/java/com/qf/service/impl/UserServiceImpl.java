package com.qf.service.impl;

import com.qf.dao.UserMapper;
import com.qf.pojo.User;
import com.qf.pojo.UserExample;
import com.qf.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public int loginUser(String email ,String password) {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andEmailEqualTo(email);
        List<User> list = userMapper.selectByExample(userExample);
        if (list.size() != 0) {
            if(list.get(0).getPassword().equals(password)) {
                return 1;
            }else {
                return -1;
            }
        }else {
            return -1;
        }
    }


    @Override
    public int insertUser(User user,String psw_again) {
        int i = userMapper.insert(user);
        if (i > 0) {
            if (user.getPassword().equals(psw_again)) {
                return 1;
            }else {
                return -1;
            }
        }
        return -1;
    }

    @Override
    public User findByEmail(String email) {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andEmailEqualTo(email);
        List<User> list = userMapper.selectByExample(userExample);
        return list.get(0);
    }

    @Override
    public int updateUser(User user) {
        int i = userMapper.updateByPrimaryKey(user);
        return i;
    }


}
