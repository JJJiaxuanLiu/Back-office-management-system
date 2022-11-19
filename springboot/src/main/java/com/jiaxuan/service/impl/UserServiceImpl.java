package com.jiaxuan.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiaxuan.entity.User;
import com.jiaxuan.mapper.UserMapper;
import com.jiaxuan.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Override
    public List<User> findAll() {
        return this.list();
    }

    @Override
    public boolean saveUser(User user) {
        return this.save(user);
    }

    @Override
    public boolean updateByUserId(User user) {
        return this.updateById(user);
    }

    @Override
    public boolean deleteByUserId(Integer id) {
        return this.removeById(id);
    }

    @Override
    public boolean deleteByUserIds(String ids) {
        String[] split = ids.split(",");

        List<String> list = new ArrayList<>(split.length);
        Collections.addAll(list,split);
        return this.removeByIds(list);
    }

//    @Autowired
//    UserMapper userMapper;
//
//    public List<User> findAll(){
//        return userMapper.findAll();
//    }
//
//    public int save(User user) {
//        return userMapper.insert(user);
//    }
//
//    public Integer updateById(User user) {
//        return userMapper.update(user);
//    }


}
