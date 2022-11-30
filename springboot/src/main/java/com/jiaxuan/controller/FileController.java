package com.jiaxuan.controller;


import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jiaxuan.commom.Result;
import com.jiaxuan.entity.Files;
import com.jiaxuan.entity.User;
import com.jiaxuan.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jiaxuan
 * @since 2022-11-28
 */
@RestController
@RequestMapping("/file")
public class FileController {

    @Value("${files.upload.path}") //读取yml文件中的路径
    private String uploadFilePath;

    @Autowired
    private FileService fileService;

    /**
     * 分页查询
     * @param pageNum
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public Page<Files> page(@RequestParam int pageNum,
                           @RequestParam int pageSize,
                           @RequestParam String name
                            ){
        Page<Files> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Files> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Files::getIsDelete, false);
        queryWrapper.like(name != null, Files::getName, name);
        queryWrapper.orderByDesc(Files::getId);
        return fileService.page(page,queryWrapper);

    }

    /**
     * 文件上传
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping("/upload")
    public String update(@RequestParam MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        long size = file.getSize();
        String type = FileUtil.extName(originalFilename);

        //定义文件唯一标识码，并将文件储存到磁盘
        String uuid = IdUtil.fastSimpleUUID();
        String fileName = uuid + StrUtil.DOT + type;
        File uploadFile = new File(uploadFilePath + fileName);
        //判断是否存在目录，不存在就创建
        if(!uploadFile.getParentFile().exists()){
            uploadFile.getParentFile().mkdir();
        }

        //创建文件,储存到磁盘
        file.transferTo(uploadFile);
        //获取md5
        String md5 = SecureUtil.md5(uploadFile);
        //通过比对数据库的md5标示，决定是否上传到数据库
        Files one = md5Check(md5);
        String url;
        if(one == null){ //数据库中不存在该文件，就存储到磁盘
            url = "http://localhost:9090/file/" + fileName;
//            //储存到磁盘
//            file.transferTo(uploadFile);
        }else { //数据库中存在，则使用说明磁盘中已经存在，直接使用存在的url,删除磁盘中的文件
            url = one.getUrl();
            uploadFile.delete();
        }
        //存储到数据库
        Files files = new Files();
        files.setName(originalFilename);
        files.setSize(size/1024);
        files.setType(type);
        files.setUrl(url);
        files.setMd5(md5);
        files.setIsDelete(false);
        files.setEnable(true);
        fileService.save(files);
        return url;
    }

    /**
     * 文件下载
     * @param fileName
     * @param response
     * @throws IOException
     */
    @GetMapping("/{fileName}")
    public void download(@PathVariable String fileName, HttpServletResponse response) throws IOException {
        //根据文件统一标识码获取文件
        File uploadFile = new File(uploadFilePath + fileName);
        //设置输出流的格式
        ServletOutputStream outputStream = response.getOutputStream();
        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName,"UTF-8"));
        response.setContentType("application/octet-stream");

        //读取文件的字节流
        outputStream.write(FileUtil.readBytes(uploadFile));
        outputStream.flush();
        outputStream.close();
    }

    /**
     * 查找数据库中的md5
     * @param md5
     * @return
     */
    public Files md5Check(String md5){
        //通过比对数据库的md5标示，决定是否上传到数据库
        LambdaQueryWrapper<Files> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(!md5.isEmpty(), Files::getMd5, md5);
        List<Files> list = fileService.list(queryWrapper);
        if(list.isEmpty()){
            return null;
        }else{
            Files one = list.get(0);
            return one;
        }

    }

    /**
     * 删除多个文件
     * @param stringids
     * @return
     */
    @DeleteMapping("del/batch")
    public Result deleteUsersById(@RequestParam String stringids){
        return fileService.deleteByIds(stringids);
    }

    /**
     * 根据id删除文件
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result deleteUserById(@PathVariable Integer id){
        return fileService.deleteById(id);
    }


    @PutMapping
    public Result editUser(@RequestBody Files file){
        return fileService.updateFile(file);
    }


}

