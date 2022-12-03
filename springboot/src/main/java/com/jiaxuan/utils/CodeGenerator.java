package com.jiaxuan.utils;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;

import java.util.Collections;

/**
 * 代码生成器
 */
public class CodeGenerator {
    public static void main(String[] args) {
        generate();

    }


    private static void generate(){
        FastAutoGenerator.create("jdbc:mysql://localhost:3306/backmanagement", "root", "root")
                .globalConfig(builder -> {
                    builder.author("jiaxuan") // 设置作者
//                            .enableSwagger() // 开启 swagger 模式
//                            .fileOverride() // 覆盖已生成文件
                            .outputDir("D:\\javapractice\\SpringPractice\\后台管理\\springboot\\src\\main\\java"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.jiaxuan") // 设置父包名
                            .moduleName("") // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml, "D:\\javapractice\\SpringPractice\\后台管理\\springboot\\src\\main\\resources\\mapper\\")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.entityBuilder().enableLombok(); //开启lombok
                    builder.mapperBuilder().enableMapperAnnotation(); //添加Mapper注解
                    builder.controllerBuilder().enableHyphenStyle() //开启驼峰转连字符
                            .enableRestStyle();                    //开启rest风格
                    builder.addInclude("sys_menu") // 设置需要生成的表名
                            .addTablePrefix("t_", "c_","sys_"); // 设置过滤表前缀
                })
//                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}
