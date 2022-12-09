package com.jiaxuan.dto;

import com.jiaxuan.entity.Menu;
import lombok.Data;

import java.util.List;

@Data
public class UserDto {

    private String username;
    private String password;
    private String nickname;
    private String avatarUrl;
    private String token;
    private String role;
    private List<Menu> menus;
}
