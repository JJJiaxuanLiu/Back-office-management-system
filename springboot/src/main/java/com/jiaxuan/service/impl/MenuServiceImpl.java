package com.jiaxuan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jiaxuan.commom.Constants;
import com.jiaxuan.commom.Result;
import com.jiaxuan.entity.Menu;
import com.jiaxuan.exception.ServiceException;
import com.jiaxuan.mapper.MenuMapper;
import com.jiaxuan.service.MenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jiaxuan
 * @since 2022-12-03
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public Result getAllMenus(String name) {
        //条件查询
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(name != null, Menu::getName, name);
        List<Menu> list = menuMapper.selectList(queryWrapper);
        //查询pid为null 的一级父级菜单
        List<Menu> parentNode = list.stream().filter(menu -> menu.getPId() == null).collect(Collectors.toList());
        //找出二级子菜单
        for (Menu menu : parentNode) {
            //pid等于父级id的就是二级子菜单
            menu.setChildren(list.stream().filter(m -> menu.getId().equals(m.getPId())).collect(Collectors.toList()));

        }

        return Result.success(parentNode);
    }

    @Override
    public Result getOneMenu(Integer id) {
        if(id == null) throw new ServiceException(Constants.CODE_401, "id为空！");
        Menu Menu = menuMapper.selectById(id);
        return Result.success(Menu);
    }

    @Override
    public Result addMenu(Menu menu) {
        if (menu == null) throw new ServiceException(Constants.CODE_401, "保存失败，菜单为空！");
        menuMapper.insert(menu);
        return Result.success();
    }

    @Override
    public Result updateMenu(Menu menu) {
        menuMapper.updateById(menu);
        return Result.success();
    }

    @Override
    public Result deleteOneMenu(Integer id) {
        menuMapper.deleteById(id);
        return Result.success();
    }

    @Override
    public Result delBatchMenus(String stringids) {
        if(StringUtils.isEmpty(stringids)){
            throw new ServiceException(Constants.CODE_401, "菜单为空！");
        }
        String[] split = stringids.split(",");
        List<String> stringList = Arrays.asList(split);

        menuMapper.deleteBatchIds(stringList);
        return Result.success("批量删除成功");
    }

    @Override
    public Page<Menu> page(Integer pageNum, Integer pageSize, String name) {
        Page<Menu> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(name != null, Menu::getName, name);
        return this.page(page,queryWrapper);
    }

}
