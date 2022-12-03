package com.jiaxuan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jiaxuan.commom.Constants;
import com.jiaxuan.commom.Result;
import com.jiaxuan.entity.Role;
import com.jiaxuan.exception.ServiceException;
import com.jiaxuan.mapper.RoleMapper;
import com.jiaxuan.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jiaxuan
 * @since 2022-12-01
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public Result getAllRoles() {
        List<Role> roles = this.list();
        return Result.success(roles);
    }

    @Override
    public Result getOneRole(Integer id) {
        if(id == null) throw new ServiceException(Constants.CODE_401, "id为空！");
        Role role = roleMapper.selectById(id);
        return Result.success(role);
    }

    @Override
    public Result addRole(Role role) {
        if (role == null) throw new ServiceException(Constants.CODE_401, "保存失败，角色为空！");
        roleMapper.insert(role);
        return Result.success();
    }

    @Override
    public Result updateRole(Role role) {
        roleMapper.updateById(role);
        return Result.success();
    }

    @Override
    public Result deleteOneRole(Integer id) {
        roleMapper.deleteById(id);
        return Result.success();
    }

    @Override
    public Result delBatchRoles(String stringids) {
        if(StringUtils.isEmpty(stringids)){
            throw new ServiceException(Constants.CODE_401, "角色为空！");
        }
        String[] split = stringids.split(",");
        List<String> stringList = Arrays.asList(split);

        roleMapper.deleteBatchIds(stringList);
        return Result.success("批量删除成功");
    }

    @Override
    public Page<Role> page(Integer pageNum, Integer pageSize, String name) {
        Page<Role> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(name != null, Role::getName, name);
        return this.page(page,queryWrapper);
    }
}
