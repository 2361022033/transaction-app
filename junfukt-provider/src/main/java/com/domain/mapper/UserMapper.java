package com.domain.mapper;

import com.domain.entity.User;
import org.apache.ibatis.annotations.Mapper;
import sun.text.normalizer.UnicodeSet;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {

}