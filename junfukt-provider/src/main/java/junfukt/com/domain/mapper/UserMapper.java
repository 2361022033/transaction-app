package junfukt.com.domain.mapper;

import junfukt.com.domain.entity.User;
import junfukt.com.controller.dto.UserUpdateReq;
import junfukt.com.utils.BasePageReq;
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