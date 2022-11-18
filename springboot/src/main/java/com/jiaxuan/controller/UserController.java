package com.jiaxuan.controller;

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
    public List<User> index(){
        return userService.findAll();
    }

    /**
     * 增加用户
     * @param user
     * @return
     */
    @PostMapping
    public Integer addUser(@RequestBody User user){
        return userService.save(user);
    }

    /**
     * 更新用户
     * @param user
     * @return
     */
    @PutMapping()
    public Integer updateById(@RequestBody User user){
        return userService.updateById(user);
    }

    /**
     * 删除用户
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Integer delete(@PathVariable Integer id){
        return userService.deleteById(id);
    }

    /**
     * 用户名分页查询
     * url: /user/page?pageNun=1&pageSize=10
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/page")
    public Map<String, Object> findPage(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize,@RequestParam("username") String username ){
        return userService.findPage(pageNum, pageSize, username);
    }


}
