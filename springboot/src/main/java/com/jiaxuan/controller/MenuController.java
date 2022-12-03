package com.jiaxuan.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jiaxuan.commom.Result;
import com.jiaxuan.entity.Menu;
import com.jiaxuan.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jiaxuan
 * @since 2022-12-03
 */
@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;


    /**
     * 查全部
     * @return
     */
    @GetMapping
    public Result getAllMenus(@RequestParam(defaultValue = "")String name){
        return menuService.getAllMenus(name);
    }

    /**
     * 根据id查
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result getOneMenu(@PathVariable Integer id){
        return menuService.getOneMenu(id);
    }

    /**
     * 新增
     * @param menu
     * @return
     */
    @PostMapping
    public Result addMenu(@RequestBody Menu menu){
        return menuService.addMenu(menu);
    }

    /**
     * 更新
     * @param menu
     * @return
     */
    @PutMapping
    public Result updateMenu(@RequestBody Menu menu){
        return menuService.updateMenu(menu);
    }

    /**
     * 根据id删除
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result deleteOneMenu(@PathVariable Integer id){
        return menuService.deleteOneMenu(id);
    }

    /**
     * 批量删除
     * @param stringids
     * @return
     */
    @DeleteMapping("/del/batch")
    public Result delBatchMenus(@RequestParam String stringids){
        return menuService.delBatchMenus(stringids);
    }

    /**
     * 分页查询
     * @param pageNum
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public Page<Menu> page(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam String name){

        return menuService.page(pageNum, pageSize, name);

    }


}

