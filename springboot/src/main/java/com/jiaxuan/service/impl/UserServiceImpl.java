package com.jiaxuan.service.impl;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jiaxuan.commom.Constants;
import com.jiaxuan.commom.Result;
import com.jiaxuan.dto.UserDto;
import com.jiaxuan.entity.User;
import com.jiaxuan.exception.ServiceException;
import com.jiaxuan.mapper.UserMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiaxuan.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jiaxuan
 * @since 2022-11-22
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public boolean addUser(User user) {
        //判断是否数据库中存在相同用户名，不存在才save
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(user.getUsername()!=null,User::getUsername,user.getUsername());
        User one = this.getOne(queryWrapper);
        if(one != null){
            return false;
        }else{
            return this.save(user);
        }
    }

    @Override
    public boolean updateUser(User user) {
        //判断数据库中是否存在相同的用户名，不同才update
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(user.getUsername() != null, User::getUsername, user.getUsername());
        User one = this.getOne(queryWrapper);
        if(one != null){
            return false;
        }else {
            return this.updateById(user);
        }
    }

    @Override
    public boolean deleteById(Integer id) {
        return this.removeById(id);
    }

    @Override
    public boolean deleteByIds(String stringids) {
        String[] split = stringids.split(",");
        ArrayList<String> list = new ArrayList<>(split.length);
        Collections.addAll(list, split);
        return this.removeByIds(list);
    }

    @Override
    public boolean export(HttpServletResponse response) throws Exception {
        //从数据库查询出所有的数据
        List<User> list = this.list();
        //通过工具类创建writer写出到磁盘路径
//        ExcelWriter writer = ExcelUtil.getWriter(filesUploadPath+"/用户信息.xlsx");
        //在内存操作，写出到浏览器
        ExcelWriter excelWriter = ExcelUtil.getWriter(true);
        //自定义标题别名，或者在实体类中加注解@Alias取别名
//        excelWriter.addHeaderAlias("username","用户名");
//        excelWriter.addHeaderAlias("password","密码");
//        excelWriter.addHeaderAlias("email","邮箱");
//        excelWriter.addHeaderAlias("nickname","昵称");
//        excelWriter.addHeaderAlias("phone","电话");
//        excelWriter.addHeaderAlias("address","地址");
//        excelWriter.addHeaderAlias("createTime","创建时间");

        //一次写出list内的对象到excel，使用默认样式，强制输出标题
        excelWriter.write(list, true);

        //设置浏览器相应的格式

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("用户信息","UTF-8");
        response.setHeader("Content-Disposition","attachment;filename="+fileName+".xlsx");

        //将excelwriter对象刷到输出流中，输出流返回浏览器
        ServletOutputStream out = response.getOutputStream();
        excelWriter.flush(out,true);
        out.close();
        excelWriter.close();
        return true;
    }

    @Override
    public boolean importFile(MultipartFile file) throws Exception {
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        List<User> list = reader.readAll(User.class);
//        System.out.println(list);
        return this.saveBatch(list);
    }

    @Override
    public Result login(UserDto userDto) {
//        根据页面提交的username查询数据库
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotEmpty(userDto.getUsername()), User::getUsername, userDto.getUsername());
        User user = this.getOne(queryWrapper);
//        没有查询到登录失败
        if(user == null){
            throw new ServiceException(Constants.CODE_600,"用户名不存在");
        }else{
            //查询到了，比对密码
            if(!userDto.getPassword().equals(user.getPassword())){
                throw new ServiceException(Constants.CODE_600, "密码错误");
            }else {
                BeanUtils.copyProperties(user,userDto);
                return Result.success("登录成功！",userDto);
            }
        }
    }

    @Override
    public Result register(UserDto userDto) {
//        根据页面提交的username查询数据库
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotEmpty(userDto.getUsername()), User::getUsername, userDto.getUsername());
        User user = this.getOne(queryWrapper);
//        查到相同用户名，抛出异常
        if(user != null){
            throw new ServiceException(Constants.CODE_600,"用户名已存在");
        }else{
            //用户名不存在，校验密码是否存在，存在则插入数据库,不存在则抛出异常
            if(StringUtils.isNotEmpty(userDto.getPassword())){
                user = new User();
                BeanUtils.copyProperties(userDto,user);
                this.save(user);
                return Result.success("注册成功！", userDto);
            }else {
                throw new ServiceException(Constants.CODE_500,"密码不存在");
            }
        }
    }

    @Override
    public Result showInfo(String username) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotEmpty(username), User::getUsername, username);
        User one = this.getOne(queryWrapper);
        if(one != null){
            return Result.success(one);
        }else {
            throw new ServiceException(Constants.CODE_500,"系统异常");
        }
    }


}
