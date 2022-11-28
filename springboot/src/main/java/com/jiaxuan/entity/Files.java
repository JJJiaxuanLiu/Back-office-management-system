package com.jiaxuan.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;


/**
 * <p>
 * 
 * </p>
 *
 * @author jiaxuan
 * @since 2022-11-28
 */
@Data
@TableName("sys_file")
public class Files implements Serializable {

    private static final long serialVersionUID = 1L;

      /**
     * id
     */
        @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

      /**
     * 文件名
     */
      private String name;

      /**
     * 文件类型
     */
      private String type;

      /**
     * 文件大小
     */
      private Long size;

      /**
      * 文件md5标示
      */
      private String md5;

      /**
     * 文件地址
     */
      private String url;

      /**
     * 是否删除
     */
      private Boolean isDelete;

      /**
     * 是否禁用链接
     */
      private Boolean enable;


}
