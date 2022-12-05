package com.jiaxuan.service;

import com.jiaxuan.commom.Result;
import com.jiaxuan.entity.RoleMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 角色菜单关系表 服务类
 * </p>
 *
 * @author jiaxuan
 * @since 2022-12-05
 */
public interface RoleMenuService extends IService<RoleMenu> {

    Result getRoleMenu(Integer roleId, List<Integer> menuIds);

    Result setRoleMenu(Integer roleId);
}
