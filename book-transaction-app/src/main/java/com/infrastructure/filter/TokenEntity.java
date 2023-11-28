package com.infrastructure.filter;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
public class TokenEntity {
    /**
     * 系统
     */
    private String root;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 过期时间
     */
    private Date overdueTime;

}