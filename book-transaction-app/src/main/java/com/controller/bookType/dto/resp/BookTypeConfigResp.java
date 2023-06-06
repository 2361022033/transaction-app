package com.controller.bookType.dto.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 图书类型输出
 * </p>
 *
 * @author sjf
 * @since 2023-03-16
 */
@Data
public class BookTypeConfigResp implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("一级类型编码")
    private String firstTypeCode;

    @ApiModelProperty("一级类型名称")
    private String firstTypeName;

    @ApiModelProperty("二级类型编码")
    private String secondTypeCode;

    @ApiModelProperty("二级类型名称")
    private String secondTypeName;

    @ApiModelProperty("三级类型编码")
    private String thirdTypeCode;

    @ApiModelProperty("三级类型名称")
    private String thirdTypeName;


}
