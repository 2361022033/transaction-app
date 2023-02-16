package com.controller.user.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Data
@Accessors(chain = true)
public class UserAddReq {

    @ApiModelProperty("用户名")
    @NotEmpty(message = "用户名不能为空")
    private String userName;

    @ApiModelProperty("密码")
    @NotEmpty(message = "密码不能为空")
    private String password;

    @ApiModelProperty("真实姓名")
    private String realName;

    @ApiModelProperty("性别:0-女,1-男")
    @NotEmpty(message = "性别不能为空")
    private Long sex;

    @ApiModelProperty("昵称")
    @NotEmpty(message = "昵称不能为空")
    private String nickName;

    @ApiModelProperty("头像")
    private String avatar;

    @ApiModelProperty("个性签名")
    private String introduction;

}