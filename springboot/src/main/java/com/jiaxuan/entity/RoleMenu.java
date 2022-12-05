package com.jiaxuan.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import lombok.Data;


/**
 * <p>
 * 角色菜单关系表
 * </p>
 *
 * @author jiaxuan
 * @since 2022-12-05
 */
@Data
@TableName("sys_role_menu")
public class RoleMenu implements Serializable {

    private static final long serialVersionUID = 1L;

      /**
     * 角色id
     */
        private Integer roleId;

      /**
     * 菜单id
     */
      private Integer menuId;


}
