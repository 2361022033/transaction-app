package junfukt.com.controller.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Data
@Accessors(chain = true)
public class UserResp implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * id
     */
    @Id
    private Long id;
    /**
     * 用户id
     */
    private String userId;
    /**
     * 工号
     */
    private String staffNo;
    /**
     * 真实姓名
     */
    private String realName;
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
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date updateTime;

}