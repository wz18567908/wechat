package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.UserDao;
import com.example.demo.entity.User;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    public User selectUser(int id){
        return userDao.findUserById(id);
    }

    public void saveUser(User user) {
        userDao.saveUser(user);
    }

//    @Transactional
//    public void insertService(){
//        HashMap<String, Object> map = new HashMap<String, Object>();
//        map.put("city", "西安");
//        map.put("num", 9421);
//
//        happinessDao.insertHappiness(map);
//        int a = 1 / 0; //模拟故障
//        happinessDao.insertHappiness(map);
//    }
}
