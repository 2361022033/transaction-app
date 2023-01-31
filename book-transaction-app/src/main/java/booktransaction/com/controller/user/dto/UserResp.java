package booktransaction.com.controller.user.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@Accessors(chain = true)
public class UserResp implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("用户id")
    private String userId;

    @ApiModelProperty("工号")
    private String staffNo;

    @ApiModelProperty("真实姓名")
    private String realName;

    @ApiModelProperty("昵称")
    private String nickName;

    @ApiModelProperty("头像")
    private String avatar;

    @ApiModelProperty("个性签名")
    private String introduction;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("修改时间")
    private Date updateTime;

}