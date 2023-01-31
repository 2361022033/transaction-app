package booktransaction.com.controller.user.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
@Accessors(chain = true)
public class UserAddReq implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 用户id
     */
    @NotEmpty(message = "用户号不能为空")
    private String userId;
    /**
     * 工号
     */
    @NotEmpty(message = "工号不能为空")
    private String staffNo;
    /**
     * 真实姓名
     */
    @NotEmpty(message = "真实姓名不能为空")
    private String realName;
    /**
     * 昵称
     */
    @NotEmpty(message = "昵称不能为空")
    private String nickName;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 个性签名
     */
    private String introduction;

}