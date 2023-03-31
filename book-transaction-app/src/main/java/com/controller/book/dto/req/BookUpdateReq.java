package com.controller.book.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Accessors(chain = true)
public class BookUpdateReq implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("书名")
    private String bookName;

    @ApiModelProperty("作者")
    private String writer;

    @ApiModelProperty("出版社")
    private String publishingHouse;

    @ApiModelProperty("描述")
    private String description;

    @ApiModelProperty("原价")
    private BigDecimal priceOriginal;

    @ApiModelProperty("在售价格")
    private BigDecimal priceNow;

    @ApiModelProperty("三级类型编码")
    private String thirdTypeCode;

    @ApiModelProperty("图片一地址")
    private String imageOne;

    @ApiModelProperty("图片二地址")
    private String imageTwo;

    @ApiModelProperty("图片三地址")
    private String imageThree;

    @ApiModelProperty("图片四地址")
    private String imageFour;

    @ApiModelProperty("视频地址")
    private String video;

}