package com.jiaxuan.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jiaxuan.commom.Result;
import com.jiaxuan.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jiaxuan.entity.Menu;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jiaxuan
 * @since 2022-12-03
 */
public interface MenuService extends IService<Menu> {

    Result getAllMenus(String name);

    Result getOneMenu(Integer id);

    Result addMenu(Menu menu);

    Result updateMenu(Menu menu);

    Result deleteOneMenu(Integer id);

    Result delBatchMenus(String stringids);

    Page<Menu> page(Integer pageNum, Integer pageSize, String name);

}
