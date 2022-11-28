package com.jiaxuan.controller;


import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.jiaxuan.entity.Files;
import com.jiaxuan.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;

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

    @PostMapping("/upload")
    public String update(@RequestParam MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        long size = file.getSize();
        String type = FileUtil.extName(originalFilename);

        //先存到磁盘
        File uploadParentFile = new File(uploadFilePath);
        //判断是否存在目录，不存在就创建
        if(!uploadParentFile.exists()){
            uploadParentFile.mkdirs();
        }
        //定义文件唯一标识码，并将文件储存
        String uuid = IdUtil.fastSimpleUUID();
        String fileUUID = uuid + StrUtil.DOT + type;
        File uploadFile = new File(uploadFilePath + fileUUID);
        //获取md5
        String md5 = SecureUtil.md5(uploadFile);
        file.transferTo(uploadFile);

        //通过比对数据库的md5标示，决定是否上传到数据库

        String url = "http://localhost:9090/file/" + fileUUID;
        //存储到数据库
        Files files = new Files();
        files.setName(originalFilename);
        files.setSize(size);
        files.setType(type);
        files.setUrl(url);
        files.setMd5(md5);
        fileService.save(files);
        return url;
    }

    @GetMapping("/{fileUUID}")
    public void download(@PathVariable String fileUUID, HttpServletResponse response) throws IOException {
        //根据文件统一标识码获取文件
        File uploadFile = new File(uploadFilePath + fileUUID);
        //设置输出流的格式
        ServletOutputStream outputStream = response.getOutputStream();
        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileUUID,"UTF-8"));
        response.setContentType("application/octet-stream");

        //读取文件的字节流
        outputStream.write(FileUtil.readBytes(uploadFile));
        outputStream.flush();
        outputStream.close();
    }
}

