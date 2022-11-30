package com.jiaxuan.service;

import com.jiaxuan.commom.Result;
import com.jiaxuan.entity.Files;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jiaxuan
 * @since 2022-11-28
 */
public interface FileService extends IService<Files> {

    Result deleteByIds(String stringids);

    Result deleteById(Integer id);

    Result updateFile(Files file);
}
