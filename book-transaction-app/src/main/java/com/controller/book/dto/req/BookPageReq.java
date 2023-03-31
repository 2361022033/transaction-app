package com.controller.book.dto.req;

import com.infrastructure.page.BasePageReq;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Accessors(chain = true)
public class BookPageReq extends BasePageReq implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty("书名")
    private String bookName;

    @ApiModelProperty("作者")
    private String writer;

    @ApiModelProperty("最低价格")
    private BigDecimal minPriceNow;

    @ApiModelProperty("最高价格")
    private BigDecimal maxPriceNow;

    @ApiModelProperty("一级类型编码")
    private String firstTypeCode;

    @ApiModelProperty("二级类型编码")
    private String secondTypeCode;

    @ApiModelProperty("三级类型编码")
    private String thirdTypeCode;

}