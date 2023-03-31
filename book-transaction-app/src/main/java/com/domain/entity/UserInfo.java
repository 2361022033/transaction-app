package com.domain.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 用户信息表
 * </p>
 *
 * @author sjf
 * @since 2023-03-16
 */
@Table(name = "user_info")
@Data
@Accessors(chain = true)
public class UserInfo implements Serializable {

    private static final long serialVersionUID = 1L;

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
     * 昵称
     */
    private String nickName;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 性别:0-女;1-男
     */
    private Integer sex;

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
    private BigDecimal balance;

    /**
     * 已买数量
     */
    private Integer bugNumber;

    /**
     * 已售数量
     */
    private Integer soldOutNumber;

    /**
     * 在售数量
     */
    private Integer onSaleNumber;

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