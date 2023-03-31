package com.controller.user.convert;

import com.controller.user.dto.req.UserAddReq;
import com.controller.user.dto.resp.LookOtherResp;
import com.controller.user.dto.resp.MyInfoResp;
import com.domain.entity.UserInfo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserConvert {
    UserConvert INSTANCE = Mappers.getMapper(UserConvert.class);
    UserInfo UserAddReq2Info(UserAddReq req);
    LookOtherResp userInfo2LookOtherResp(UserInfo userInfo);
    MyInfoResp userInfo2MyInfoResp(UserInfo userInfo);

}
