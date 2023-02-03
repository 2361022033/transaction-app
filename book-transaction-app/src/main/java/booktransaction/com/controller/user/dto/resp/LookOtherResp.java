package booktransaction.com.controller.user.dto.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Accessors(chain = true)
public class LookOtherResp {
    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("性别")
    private Long sex;

    @ApiModelProperty("昵称")
    private String nickName;

    @ApiModelProperty("头像")
    private String avatar;

    @ApiModelProperty("个性签名")
    private String introduction;

    @ApiModelProperty("已售数量")
    private Long soldOutNumber;

    @ApiModelProperty("在售数量")
    private Long onSaleNumber;


}