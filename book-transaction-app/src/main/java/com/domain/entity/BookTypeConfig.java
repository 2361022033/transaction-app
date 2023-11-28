package com.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 图书类型配置表
 * </p>
 *
 * @author sjf
 * @since 2023-03-16
 */
@TableName("book_type_config")
@Data
@Accessors(chain = true)
public class BookTypeConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * PK
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    /**
     * 一级类型编码
     */
    private String firstTypeCode;

    /**
     * 一级类型名称
     */
    private String firstTypeName;

    /**
     * 二级类型编码
     */
    private String secondTypeCode;

    /**
     * 二级类型名称
     */
    private String secondTypeName;

    /**
     * 三级类型编码
     */
    private String thirdTypeCode;

    /**
     * 三级类型名称
     */
    private String thirdTypeName;

    /**
     * 是否删除
     */
    private Boolean isDeleted;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

}
