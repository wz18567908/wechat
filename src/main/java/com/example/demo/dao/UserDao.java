package com.example.demo.dao;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.entity.User;

@Mapper
public interface UserDao {
    User findUserById(int id);
    User findUserByName(String userName);
    void saveUser(User user);
}
