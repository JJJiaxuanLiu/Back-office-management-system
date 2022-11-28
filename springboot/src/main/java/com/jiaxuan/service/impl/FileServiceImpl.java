package com.jiaxuan.service.impl;

import com.jiaxuan.entity.Files;
import com.jiaxuan.mapper.FileMapper;
import com.jiaxuan.service.FileService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jiaxuan
 * @since 2022-11-28
 */
@Service
public class FileServiceImpl extends ServiceImpl<FileMapper, Files> implements FileService {

}
