package co.istad.idata.mapper;

import co.istad.idata.domains.User;
import co.istad.idata.feature.user.dto.UserCreateRequest;
import co.istad.idata.feature.user.dto.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.control.MappingControl;
import org.springframework.boot.autoconfigure.security.SecurityProperties;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User fromUserCreateRequest(UserCreateRequest createRequest);

    UserResponse toUserResponse(User user);

    List<UserResponse> toUserResponseList(List<User> users);

}
