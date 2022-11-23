package com.jiaxuan.service.impl;

import com.jiaxuan.entity.User;
import com.jiaxuan.mapper.UserMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiaxuan.service.UserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jiaxuan
 * @since 2022-11-22
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public boolean addUser(User user) {
        return this.save(user);
    }

    @Override
    public boolean updateUser(User user) {
        return this.updateById(user);
    }

    @Override
    public boolean deleteById(Integer id) {
        return this.removeById(id);
    }

    @Override
    public boolean deleteByIds(String stringids) {
        String[] split = stringids.split(",");
        ArrayList<String> list = new ArrayList<>(split.length);
        Collections.addAll(list, split);
        return this.removeByIds(list);
    }
}
