package booktransaction.com.controller.user.convert;

import booktransaction.com.controller.user.dto.req.UserAddReq;
import booktransaction.com.domain.entity.UserInfo;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-01-31T14:41:22+0800",
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
}
