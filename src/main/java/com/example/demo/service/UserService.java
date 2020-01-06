package com.example.demo.service;

import com.example.demo.entity.User;

public interface UserService {

    public User selectUser(int id);

    public void saveUser(User user);
}
