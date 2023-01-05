package com.domain.mapper;

import com.domain.entity.User;
import junfukt.com.controller.dto.UserUpdateReq;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.BaseMapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    User selectByUserId(@Param("userId") String userId);
    Long updateByUsetId(UserUpdateReq req);
}