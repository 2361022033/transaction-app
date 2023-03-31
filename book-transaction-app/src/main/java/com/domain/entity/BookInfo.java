package com.domain.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 图书信息表
 * </p>
 *
 * @author sjf
 * @since 2023-03-16
 */
@Table(name = "book_info")
@Data
@Accessors(chain = true)
public class BookInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * PK
     */
    @Id
    private Long id;

    /**
     * 书名
     */
    private String bookName;

    /**
     * 作者
     */
    private String writer;

    /**
     * 出版社
     */
    private String publishingHouse;

    /**
     * 描述
     */
    private String description;

    /**
     * 原价
     */
    private BigDecimal priceOriginal;

    /**
     * 在售价格
     */
    private BigDecimal priceNow;

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
     * 图片一地址
     */
    private String imageOne;

    /**
     * 图片二地址
     */
    private String imageTwo;

    /**
     * 图片三地址
     */
    private String imageThree;

    /**
     * 图片四地址
     */
    private String imageFour;

    /**
     * 视频地址
     */
    private String video;

    /**
     * 卖家id
     */
    private Long sallerId;

    /**
     * 浏览数
     */
    private Integer browseNumber;

    /**
     * 收藏数
     */
    private Integer favoriteNumber;

    /**
     * 状态:1(在售),0(已售)
     */
    private Integer status;

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