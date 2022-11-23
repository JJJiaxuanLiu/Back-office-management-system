package com.jiaxuan.mapper;

import com.jiaxuan.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jiaxuan
 * @since 2022-11-22
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
