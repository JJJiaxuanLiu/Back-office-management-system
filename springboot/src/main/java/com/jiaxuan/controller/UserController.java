package com.jiaxuan.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jiaxuan.entity.User;
import com.jiaxuan.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 查询用户全部信息
     * @return
     */
    @GetMapping
    public List<User> findAll(){
        return userService.findAll();
    }

    /**
     * 增加用户
     * @param user
     * @return
     */
    @PostMapping
    public boolean addUser(@RequestBody User user){
        return userService.saveUser(user);
    }

    /**
     * 更新用户
     * @param user
     * @return
     */
    @PutMapping()
    public boolean updateByUserId(@RequestBody User user){
        return userService.updateByUserId(user);
    }

    /**
     * 删除用户
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Integer id){
        return userService.deleteByUserId(id);
    }

    @DeleteMapping("/del/batch")
    public boolean deleteBatch(@RequestParam String stringids){
        return userService.deleteByUserIds(stringids);
    }

    /**
     * 用户名、邮箱、地址模糊分页查询
     * @param pageNum
     * @param pageSize
     * @param username
     * @param email
     * @param address
     * @return
     */
    @GetMapping("/page")
    public Page<User> findPage(@RequestParam(value="pageNum") Integer pageNum,
                               @RequestParam(value = "pageSize") Integer pageSize,
                               @RequestParam(value = "username",defaultValue = "") String username,
                               @RequestParam(value = "email",defaultValue = "") String email,
                               @RequestParam(value = "address",defaultValue = "") String address) {
        Page<User> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(username != null, User::getUsername,username);
        queryWrapper.like(email != null, User::getEmail,email);
        queryWrapper.like(address != null, User::getAddress,address);
        queryWrapper.orderByDesc(User::getId);
        return userService.page(page, queryWrapper);

    }

//    /**
//     * 用户名分页查询
//     * url: /user/page?pageNun=1&pageSize=10
//     * @param pageNum
//     * @param pageSize
//     * @return
//     */
//    @GetMapping("/page")
//    public Map<String, Object> findPage(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize,@RequestParam("username") String username ){
//        return userService.findPage(pageNum, pageSize, username);
//    }


}
