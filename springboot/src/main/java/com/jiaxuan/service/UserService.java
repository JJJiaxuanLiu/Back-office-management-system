package com.jiaxuan.service;

import com.jiaxuan.commom.Result;
import com.jiaxuan.dto.UserDto;
import com.jiaxuan.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jiaxuan
 * @since 2022-11-22
 */

public interface UserService extends IService<User> {

    boolean addUser(User user);

    boolean updateUser(User user);

    boolean deleteById(Integer id);

    boolean deleteByIds(String stringids);

    boolean export(HttpServletResponse response) throws Exception;

    boolean importFile(MultipartFile file) throws IOException, Exception;

    Result login(UserDto userDto);

    Result register(UserDto userDto);

    Result showInfo(String username);
}
