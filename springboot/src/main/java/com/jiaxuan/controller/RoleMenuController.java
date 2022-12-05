package com.jiaxuan.controller;


import com.jiaxuan.commom.Result;
import com.jiaxuan.service.RoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 角色菜单关系表 前端控制器
 * </p>
 *
 * @author jiaxuan
 * @since 2022-12-05
 */
@RestController
@RequestMapping("/rolemenu")
public class RoleMenuController {

    @Autowired
    private RoleMenuService roleMenuService;

    //根据角色id，新增选择的菜单
    @PostMapping("/{roleId}")
    public Result setRoleMenu(@PathVariable Integer roleId, @RequestBody List<Integer> menuIds){
        return roleMenuService.getRoleMenu(roleId, menuIds);
    }


    //回显选择的菜单
    @GetMapping("/{roleId}")
    public Result setRoleMenu(@PathVariable Integer roleId){
        return roleMenuService.setRoleMenu(roleId);
    }

}

