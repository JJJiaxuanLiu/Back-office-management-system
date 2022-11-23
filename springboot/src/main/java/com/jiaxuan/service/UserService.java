package com.jiaxuan.service;

import com.jiaxuan.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jiaxuan
 * @since 2022-11-22
 */

public interface UserService extends IService<User> {

    boolean addUser(User user);

    boolean updateUser(User user);

    boolean deleteById(Integer id);

    boolean deleteByIds(String stringids);
}
