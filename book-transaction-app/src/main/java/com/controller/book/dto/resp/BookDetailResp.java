package com.controller.book.dto.resp;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Accessors(chain = true)
public class BookDetailResp implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * id
     */
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;
}