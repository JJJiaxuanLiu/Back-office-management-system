package com.jiaxuan.service;

import com.jiaxuan.entity.User;
import com.jiaxuan.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {
    @Autowired
    UserMapper userMapper;

    /**
     * 查询全部
     * @return
     */
    public List<User> findAll(){
        return userMapper.findAll();
    }

    /**
     * 新增用户
     * @param user
     * @return
     */
    public int save(User user) {
        return userMapper.insert(user);
    }

    /**
     * 更新用户信息
     * @param user
     * @return
     */
    public Integer updateById(User user) {
        return userMapper.update(user);
    }

    /**
     * 删除用户
     * @param id
     * @return
     */
    public Integer deleteById(Integer id) {
        return userMapper.deleteById(id);
    }

    /**
     * 分页
     * @param pageNum
     * @param pageSize
     * @return
     */
    public Map<String, Object> findPage(Integer pageNum, Integer pageSize,String username) {
        pageNum = (pageNum-1) * pageSize;
        username = "%" + username + "%";
        List<User> data = userMapper.findPage(pageNum, pageSize,username);
        Integer total = userMapper.totalPage(username);
        Map<String,Object> map = new HashMap<>();
        map.put("data",data);
        map.put("total",total);
        return map;
    }
}
