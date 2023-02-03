package booktransaction.com.controller.user.convert;

import booktransaction.com.controller.user.dto.req.UserAddReq;
import booktransaction.com.controller.user.dto.resp.LookOtherResp;
import booktransaction.com.domain.entity.UserInfo;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-02-03T15:46:00+0800",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 1.8.0_191 (Oracle Corporation)"
)
public class UserConvertImpl implements UserConvert {

    @Override
    public UserInfo convert(UserAddReq req) {
        if ( req == null ) {
            return null;
        }

        UserInfo userInfo = new UserInfo();

        userInfo.setUserName( req.getUserName() );
        userInfo.setPassword( req.getPassword() );
        userInfo.setRealName( req.getRealName() );
        userInfo.setSex( req.getSex() );
        userInfo.setNickName( req.getNickName() );
        userInfo.setAvatar( req.getAvatar() );
        userInfo.setIntroduction( req.getIntroduction() );

        return userInfo;
    }

    @Override
    public LookOtherResp convert(UserInfo userInfo) {
        if ( userInfo == null ) {
            return null;
        }

        LookOtherResp lookOtherResp = new LookOtherResp();

        lookOtherResp.setId( userInfo.getId() );
        lookOtherResp.setSex( userInfo.getSex() );
        lookOtherResp.setNickName( userInfo.getNickName() );
        lookOtherResp.setAvatar( userInfo.getAvatar() );
        lookOtherResp.setIntroduction( userInfo.getIntroduction() );
        lookOtherResp.setSoldOutNumber( userInfo.getSoldOutNumber() );
        lookOtherResp.setOnSaleNumber( userInfo.getOnSaleNumber() );

        return lookOtherResp;
    }
}
