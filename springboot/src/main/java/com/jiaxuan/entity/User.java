package com.jiaxuan.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class User {
    private Integer id;
    private String username;
    @JsonIgnore    //不展示password
    private String password;
    private String nickname;
    private String phone;
    private String email;
    private String address;

}
