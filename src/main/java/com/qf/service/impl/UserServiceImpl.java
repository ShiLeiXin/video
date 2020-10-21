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
    public List<User> findByEmailAndPsw(User user) {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andEmailEqualTo(user.getEmail());
        criteria.andPasswordEqualTo(user.getPassword());

        return userMapper.selectByExample(userExample);
    }

    @Override
    public List<User> findAllUsers() {
        return userMapper.selectByExample(null);
    }

    @Override
    public List<User> findByUserAccount(String userAccount) {
        UserExample userExample = new UserExample();

        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andEmailEqualTo(userAccount);
        return userMapper.selectByExample(userExample);
    }

    @Override
    public void updateUser(User user)
    {
        userMapper.updateByPrimaryKeySelective(user);
    }



    @Override
    public List<User> findByEmail(User user) {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andEmailEqualTo(user.getEmail());
        return userMapper.selectByExample(userExample);
    }

    @Override
    public Integer insertUser(User user) {
        return userMapper.insertSelective(user);
    }


}
