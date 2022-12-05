package com.jiaxuan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jiaxuan.commom.Result;
import com.jiaxuan.entity.RoleMenu;
import com.jiaxuan.mapper.RoleMenuMapper;
import com.jiaxuan.service.RoleMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 角色菜单关系表 服务实现类
 * </p>
 *
 * @author jiaxuan
 * @since 2022-12-05
 */
@Service
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements RoleMenuService {

    @Transactional
    @Override
    public Result getRoleMenu(Integer roleId, List<Integer> menuIds) {
        //删除已经存在的关系
        LambdaQueryWrapper<RoleMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(roleId != null, RoleMenu::getRoleId, roleId);
        this.remove(queryWrapper);
        //新增关系
        for (Integer menuId : menuIds) {
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setRoleId(roleId);
            roleMenu.setMenuId(menuId);
            this.save(roleMenu);
        }
        return Result.success();
    }

    @Override
    public Result setRoleMenu(Integer roleId) {
        LambdaQueryWrapper<RoleMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(roleId != null, RoleMenu::getRoleId, roleId);
        List<RoleMenu> list = this.list(queryWrapper);
        ArrayList<Integer> menuIdList = new ArrayList<>(list.size());
        for (RoleMenu roleMenu : list) {
            menuIdList.add(roleMenu.getMenuId());
        }
        return Result.success(menuIdList);
    }
}
