package com.controller.book.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Accessors(chain = true)
public class BookAddReq implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("书名")
    @NotEmpty(message = "书名不能为空!")
    private String bookName;

    @ApiModelProperty("作者")
    @NotEmpty(message = "作者不能为空!")
    private String writer;

    @ApiModelProperty("出版社")
    @NotEmpty(message = "出版社不能为空!")
    private String publishingHouse;

    @ApiModelProperty("描述")
    @NotEmpty(message = "描述不能为空!")
    private String description;

    @ApiModelProperty("原价")
    @NotNull(message = "原价不能为空!")
    private BigDecimal priceOriginal;

    @ApiModelProperty("在售价格")
    @NotNull(message = "在售价格不能为空!")
    private BigDecimal priceNow;

    @ApiModelProperty("三级类型编码")
    @NotEmpty(message = "三级类型编码不能为空!")
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

    @ApiModelProperty("卖家id")
    private Long sallerId;
}