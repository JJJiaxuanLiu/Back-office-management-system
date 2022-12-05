package com.jiaxuan.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jiaxuan.commom.Constants;
import com.jiaxuan.commom.Result;
import com.jiaxuan.entity.Dict;
import com.jiaxuan.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jiaxuan
 * @since 2022-12-05
 */
@RestController
@RequestMapping("/dict")
public class DictController {

    @Autowired
    private DictService dictService;

    @GetMapping("/icons")
    public Result getIcons(){
        LambdaQueryWrapper<Dict> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Dict::getType, Constants.DICT_ICON);
        List<Dict> list = dictService.list(queryWrapper);
        return Result.success(list);
    }

}

