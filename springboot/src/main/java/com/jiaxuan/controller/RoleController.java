package com.jiaxuan.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jiaxuan.commom.Result;
import com.jiaxuan.entity.Role;
import com.jiaxuan.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jiaxuan
 * @since 2022-12-01
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;


    /**
     * 查全部
     * @return
     */
    @GetMapping
    public Result getAllRoles(){
        return roleService.getAllRoles();
    }

    /**
     * 根据id查
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result getOneRole(@PathVariable Integer id){
        return roleService.getOneRole(id);
    }

    /**
     * 新增
     * @param role
     * @return
     */
    @PostMapping
    public Result addRole(@RequestBody Role role){
        return roleService.addRole(role);
    }

    /**
     * 更新
     * @param role
     * @return
     */
    @PutMapping
    public Result updateRole(@RequestBody Role role){
        return roleService.updateRole(role);
    }

    /**
     * 根据id删除
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result deleteOneRole(@PathVariable Integer id){
        return roleService.deleteOneRole(id);
    }

    /**
     * 批量删除
     * @param stringids
     * @return
     */
    @DeleteMapping("/del/batch")
    public Result delBatchRoles(@RequestParam String stringids){
        return roleService.delBatchRoles(stringids);
    }

    /**
     * 分页查询
     * @param pageNum
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public Page<Role> page(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam String name){

        return roleService.page(pageNum, pageSize, name);

    }




}

