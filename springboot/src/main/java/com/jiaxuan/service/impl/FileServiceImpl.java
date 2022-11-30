package com.jiaxuan.service.impl;

import com.jiaxuan.commom.Constants;
import com.jiaxuan.commom.Result;
import com.jiaxuan.entity.Files;
import com.jiaxuan.exception.ServiceException;
import com.jiaxuan.mapper.FileMapper;
import com.jiaxuan.service.FileService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    @Autowired
    private FileMapper fileMapper;

    @Override
    public Result deleteByIds(String stringids) {
        if(StringUtils.isEmpty(stringids)){
            throw new ServiceException(Constants.CODE_600,"选择文件为空");
        }
        String[] split = stringids.split(",");
        ArrayList<String> list = new ArrayList<>(split.length);
        Collections.addAll(list, split);

        List<Files> filesList = fileMapper.selectBatchIds(list);
        for (Files file : filesList) {
            file.setIsDelete(true);
        }

        return Result.success(this.updateBatchById(filesList));
    }

    @Override
    public Result deleteById(Integer id) {
        Files files = fileMapper.selectById(id);
        files.setIsDelete(true);
        return Result.success(this.updateById(files));
    }

    @Override
    public Result updateFile(Files file) {
        return Result.success("修改成功！", this.updateById(file));
    }
}
