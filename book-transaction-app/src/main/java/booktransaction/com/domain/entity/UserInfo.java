package booktransaction.com.domain.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Accessors(chain = true)
@Table(name = "user_info")
public class UserInfo {
    /**
     * PK
     */
    @Id
    private Long id;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String password;

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

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 是否删除
     */
    private Boolean isDeleted;

}