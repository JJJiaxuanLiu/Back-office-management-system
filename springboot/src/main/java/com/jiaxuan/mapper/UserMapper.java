package com.jiaxuan.mapper;

import com.jiaxuan.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {

    /**
     * 查全部
     * @return
     */
    @Select("select * from sys_user")
    List<User> findAll();

    /**
     * 新增
     * @param user
     * @return
     */
    @Insert("insert into sys_user(username,password,nickname,email,phone,address) " +
            "values(#{username},#{password},#{nickname},#{email},#{phone},#{address})")
    int insert(User user);

    /**
     * 更新
     * @param user
     * @return
     */
    int update(User user);

    /**
     * 删除
     * @param id
     * @return
     */
    @Delete("delete from sys_user where id = #{id}")
    Integer deleteById(@Param("id") Integer id);

    @Select("select * from sys_user where username like #{username} limit #{pageNum}, #{pageSize}")
    List<User> findPage(Integer pageNum, Integer pageSize, String username);

    @Select("select count(*) from sys_user where username like concat('%', #{username}, '%')")
    Integer totalPage(String username);
}
