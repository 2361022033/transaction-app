package com.domain.entity;

import lombok.Data;

import java.util.Date;

@Data
public class User {
    /**
     * id
     */
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