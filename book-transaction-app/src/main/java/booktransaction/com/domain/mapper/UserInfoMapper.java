package booktransaction.com.domain.mapper;

import booktransaction.com.domain.entity.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import tk.mybatis.mapper.common.BaseMapper;

@Mapper
public interface UserInfoMapper extends BaseMapper<UserInfo> {

}