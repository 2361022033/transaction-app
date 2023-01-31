package booktransaction.com.controller.user.dto.req;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserLoginReq {
    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String password;
}