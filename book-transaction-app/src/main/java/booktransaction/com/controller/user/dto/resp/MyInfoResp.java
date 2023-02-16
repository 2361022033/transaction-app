package booktransaction.com.controller.user.dto.resp;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class MyInfoResp {
    /**
     * PK
     */
    private Long id;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 性别
     */
    private Long sex;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 个性签名
     */
    private String introduction;

    /**
     * 账户余额
     */
    private Long balance;

    /**
     * 已买数量
     */
    private Long bugNumber;

    /**
     * 已售数量
     */
    private Long soldOutNumber;

    /**
     * 在售数量
     */
    private Long onSaleNumber;


}