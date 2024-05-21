package co.istad.idata.mapper;

import co.istad.idata.domains.User;
import co.istad.idata.feature.user.registration.RegistrationRequest;
import co.istad.idata.feature.user.dto.UserResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User fromUserCreateRequest(RegistrationRequest createRequest);

    UserResponse toUserResponse(User user);

    User fromUserResponse(UserResponse userResponse);

    List<UserResponse> toUserResponseList(List<User> users);

}
