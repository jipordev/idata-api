package co.istad.idata.feature.user;

import co.istad.idata.domains.User;
import co.istad.idata.feature.user.dto.UserCreateRequest;
import co.istad.idata.feature.user.dto.UserResponse;
import co.istad.idata.feature.user.dto.UserUpdateRequest;
import co.istad.idata.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserResponse createUser(UserCreateRequest createRequest) {

        User user = userMapper.fromUserCreateRequest(createRequest);

        userRepository.save(user);

        return userMapper.toUserResponse(user);

    }

    @Override
    public UserResponse updateUserById(Long id, UserUpdateRequest updateRequest) {

        User user = userRepository.findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "User has not been found"
                        )
                );

        return null;
    }

    @Override
    public List<UserResponse> findAll() {

        List<User> users = userRepository.findAll();

        return userMapper.toUserResponseList(users);
    }

    @Override
    public UserResponse findUserById(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "User has not been found"
                        )
                );

        return userMapper.toUserResponse(user);

    }

    @Override
    public void deleteUserById(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "User has not been found"
                        )
                );

        userRepository.delete(user);

    }
}
