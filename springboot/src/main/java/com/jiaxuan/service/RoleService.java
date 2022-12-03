package com.jiaxuan.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jiaxuan.commom.Result;
import com.jiaxuan.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jiaxuan
 * @since 2022-12-01
 */
public interface RoleService extends IService<Role> {

    Result getAllRoles();

    Result getOneRole(Integer id);

    Result addRole(Role role);

    Result updateRole(Role role);

    Result deleteOneRole(Integer id);

    Result delBatchRoles(String stringids);

    Page<Role> page(Integer pageNum, Integer pageSize, String name);
}
