package com.jiaxuan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jiaxuan.entity.User;
import com.jiaxuan.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public interface UserService extends IService<User> {

    List<User> findAll();

    boolean saveUser(User user);

    boolean updateByUserId(User user);

    boolean deleteByUserId(Integer id);

    boolean deleteByUserIds(String ids);

//    /**
//     * 查询全部
//     * @return
//     */
//    public List<User> findAll();
//
//    /**
//     * 新增用户
//     * @param user
//     * @return
//     */
//    public int save(User user);
//
//    /**
//     * 更新用户信息
//     * @param user
//     * @return
//     */
//    public Integer updateById(User user) {
//        return userMapper.update(user);
//    }
//
//    /**
//     * 删除用户
//     * @param id
//     * @return
//     */
//    public Integer deleteById(Integer id);
//
//    /**
//     * 分页
//     * @param pageNum
//     * @param pageSize
//     * @return
//     */
//    public Map<String, Object> findPage(Integer pageNum, Integer pageSize,String username) ;
}
