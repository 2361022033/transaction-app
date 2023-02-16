package booktransaction.com.controller.user.convert;

import booktransaction.com.controller.user.dto.req.UserAddReq;
import booktransaction.com.controller.user.dto.resp.LookOtherResp;
import booktransaction.com.controller.user.dto.resp.MyInfoResp;
import booktransaction.com.domain.entity.UserInfo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserConvert {
    UserConvert INSTANCE = Mappers.getMapper(UserConvert.class);
    UserInfo convert(UserAddReq req);
    LookOtherResp userInfo2LookOtherResp(UserInfo userInfo);
    MyInfoResp userInfo2MyInfoResp(UserInfo userInfo);

}
