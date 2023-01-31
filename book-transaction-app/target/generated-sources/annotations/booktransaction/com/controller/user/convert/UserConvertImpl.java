package booktransaction.com.controller.user.convert;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-01-31T13:47:37+0800",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 1.8.0_191 (Oracle Corporation)"
)
public class UserConvertImpl implements UserConvert {

    @Override
    public UserResp convert(User user) {
        if ( user == null ) {
            return null;
        }

        UserResp userResp = new UserResp();

        userResp.setId( user.getId() );
        userResp.setUserId( user.getUserId() );
        userResp.setStaffNo( user.getStaffNo() );
        userResp.setRealName( user.getRealName() );
        userResp.setNickName( user.getNickName() );
        userResp.setAvatar( user.getAvatar() );
        userResp.setIntroduction( user.getIntroduction() );
        userResp.setCreateTime( user.getCreateTime() );
        userResp.setUpdateTime( user.getUpdateTime() );

        return userResp;
    }

    @Override
    public List<UserResp> convert(List<User> user) {
        if ( user == null ) {
            return null;
        }

        List<UserResp> list = new ArrayList<UserResp>( user.size() );
        for ( User user1 : user ) {
            list.add( convert( user1 ) );
        }

        return list;
    }
}
