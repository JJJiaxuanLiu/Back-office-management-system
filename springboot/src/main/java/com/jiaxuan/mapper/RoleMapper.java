package com.jiaxuan.mapper;

import com.jiaxuan.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jiaxuan
 * @since 2022-12-01
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {


    @Select("select id from sys_role where flag = #{flag}")
    Integer selectRoleIdByFlag(@Param("flag") String flag);
}
