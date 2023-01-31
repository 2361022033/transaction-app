package booktransaction.com.domain.mapper;

import booktransaction.com.domain.entity.User;
import booktransaction.com.controller.user.dto.UserUpdateReq;
import booktransaction.com.utils.BasePageReq;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    Long countPageList(BasePageReq req);
    User selectByUserId(@Param("userId") String userId);
    Long updateByUsetId(UserUpdateReq req);
    List<User> selectPageList(BasePageReq req);

}