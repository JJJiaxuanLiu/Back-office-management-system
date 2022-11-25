package com.jiaxuan.service;

import com.jiaxuan.commom.Result;
import com.jiaxuan.dto.UserDto;
import com.jiaxuan.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jiaxuan
 * @since 2022-11-22
 */

public interface UserService extends IService<User> {

    Result addUser(User user);

    Result updateUser(User user);

    Result deleteById(Integer id);

    Result deleteByIds(String stringids);

    Result export(HttpServletResponse response) throws Exception;

    List<User> importFile(MultipartFile file) throws  Exception;

    Result login(UserDto userDto);

    Result register(UserDto userDto);

    Result showInfo(String username);

    Result checkFile(List<User> users);
}
