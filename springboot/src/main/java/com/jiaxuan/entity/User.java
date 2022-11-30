package com.jiaxuan.entity;

import cn.hutool.core.annotation.Alias;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author jiaxuan
 * @since 2022-11-22
 */
@TableName("sys_user")
@Data
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

      /**
     * id
     */
        @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

      /**
     * 用户名
     */
      @Alias("用户名")
      private String username;

      /**
     * 密码
     */
      @Alias("密码")
      @JsonIgnore
      private String password;

      /**
     * 昵称
     */
      @Alias("昵称")
      private String nickname;

      /**
     * 邮箱
     */
      @Alias("邮箱")
      private String email;

      /**
     * 电话
     */
      @Alias("电话")
      private String phone;

      /**
     * 地址
     */
      @Alias("地址")
      private String address;

      /**
     * 创建时间
     */
      @Alias("创建时间")
      private LocalDateTime createTime;

      /**
      * 用户头像url
      */
      @Alias("用户头像url")
      private String avatarUrl;

}
