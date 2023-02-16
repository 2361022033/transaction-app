package booktransaction.com.controller.user.dto.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 我的信息-出参
 */
@Data
@Accessors(chain = true)
public class MyInfoResp {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("用户名")
    private String userName;

    @ApiModelProperty("真实姓名")
    private String realName;

    @ApiModelProperty("性别")
    private Long sex;

    @ApiModelProperty("昵称")
    private String nickName;

    @ApiModelProperty("头像")
    private String avatar;

    @ApiModelProperty("个性签名")
    private String introduction;

    @ApiModelProperty("账户余额")
    private Long balance;

    @ApiModelProperty("已买数量")
    private Long bugNumber;

    @ApiModelProperty("已售数量")
    private Long soldOutNumber;

    @ApiModelProperty("在售数量")
    private Long onSaleNumber;


}