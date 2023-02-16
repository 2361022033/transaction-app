package booktransaction.com.domain.mapper;

import booktransaction.com.domain.entity.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.BaseMapper;

@Mapper
public interface UserInfoMapper extends BaseMapper<UserInfo> {

    UserInfo selectByUserName(@Param("userName") String userName);

    UserInfo selectByUserId(@Param("userId") Long userId);
}