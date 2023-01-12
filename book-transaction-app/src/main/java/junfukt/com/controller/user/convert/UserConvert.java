package junfukt.com.controller.user.convert;

import junfukt.com.controller.user.dto.UserResp;
import junfukt.com.domain.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserConvert {
    UserConvert INSTANCE = Mappers.getMapper(UserConvert.class);

    UserResp convert(User user);
    List<UserResp> convert(List<User> user);
}
