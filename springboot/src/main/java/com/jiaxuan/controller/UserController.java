package com.jiaxuan.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jiaxuan.entity.User;
import com.jiaxuan.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jiaxuan
 * @since 2022-11-22
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;


    /**
     * 分页查询
     * @param pageNum
     * @param pageSize
     * @param username
     * @param email
     * @param address
     * @return
     */
    @GetMapping("/page")
    public Page<User> page(@RequestParam int pageNum,
                     @RequestParam int pageSize,
                     @RequestParam String username,
                     @RequestParam String email,
                     @RequestParam String address){
        Page<User> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(username != null, User::getUsername, username);
        queryWrapper.like(email != null, User::getEmail, email);
        queryWrapper.like(address != null, User::getAddress, address);
        queryWrapper.orderByDesc(User::getId);
        return userService.page(page,queryWrapper);

    }

    /**
     * 新增用户
     * @param user
     * @return
     */
    @PostMapping
    public boolean addUser(@RequestBody User user){
        return userService.addUser(user);
    }

    /**
     * 修改用户信息
     * @param user
     * @return
     */
    @PutMapping
    public boolean editUser(@RequestBody User user){
        return userService.updateUser(user);
    }

    /**
     * 根据id删除单个用户
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public boolean deleteUserById(@PathVariable Integer id){
        return userService.deleteById(id);
    }

    @DeleteMapping("del/batch")
    public boolean deleteUsersById(@RequestParam String stringids){
        return userService.deleteByIds(stringids);
    }

}

