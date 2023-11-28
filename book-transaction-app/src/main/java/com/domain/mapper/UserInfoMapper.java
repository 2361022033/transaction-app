package com.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.domain.entity.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserInfoMapper extends BaseMapper<UserInfo> {

    UserInfo selectByUserName(@Param("userName") String userName);

    UserInfo selectByUserId(@Param("userId") Long userId);

    int updateOnSaleNumber(@Param("userId") Long userId);
}