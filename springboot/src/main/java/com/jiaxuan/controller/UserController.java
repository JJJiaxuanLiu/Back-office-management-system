package com.jiaxuan.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jiaxuan.commom.Result;
import com.jiaxuan.dto.UserDto;
import com.jiaxuan.entity.User;
import com.jiaxuan.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;


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
     * 登录
     * @param userDto
     * @return
     */
    @PostMapping("/login")
    public Result login(@RequestBody UserDto userDto){
        return userService.login(userDto);
    }

    /**
     * 注册
     * @param userDto
     * @return
     */
    @PostMapping("/register")
    public Result register(@RequestBody UserDto userDto){
        return userService.register(userDto);
    }

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
    public Boolean addUser(@RequestBody User user){
        return userService.addUser(user);
    }

    /**
     * 修改用户信息
     * @param user
     * @return
     */
    @PutMapping
    public Boolean editUser(@RequestBody User user){
        return userService.updateUser(user);
    }

    /**
     * 根据id删除单个用户
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Boolean deleteUserById(@PathVariable Integer id){
        return userService.deleteById(id);
    }

    /**
     * 批量删除
     * @param stringids
     * @return
     */
    @DeleteMapping("del/batch")
    public Boolean deleteUsersById(@RequestParam String stringids){
        return userService.deleteByIds(stringids);
    }

    /**
     * 文件下载
     * @param response
     * @throws Exception
     */
    @GetMapping("/export")
    public Boolean export(HttpServletResponse response) throws Exception {
        return userService.export(response);
    }

    /**
     * 文件上传
     * @param file
     * @return
     * @throws Exception
     */
    @PostMapping("/import")
    public Boolean importFile(MultipartFile file) throws Exception {
        return userService.importFile(file);
    }

    @GetMapping("/username/{username}")
    public Result showInfo(@PathVariable String username){
        return userService.showInfo(username);
    }

}

