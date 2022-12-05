package com.jiaxuan.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import lombok.Data;

/**
 * <p>
 * 
 * </p>
 *
 * @author jiaxuan
 * @since 2022-12-05
 */
@Data
@TableName("sys_dict")
public class Dict implements Serializable {

    private static final long serialVersionUID = 1L;

      /**
     * 名称
     */
      private String name;

      /**
     * 内容
     */
      private String value;

      /**
     * 类型
     */
      private String type;


}
