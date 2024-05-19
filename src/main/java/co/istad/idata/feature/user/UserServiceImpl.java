package co.istad.idata.feature.user;

import co.istad.idata.domains.Role;
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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;

    @Override
    public UserResponse createUser(UserCreateRequest createRequest) {

        if (userRepository.existsByEmail(createRequest.email())){
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Email is already existed"
            );
        }

        if (!createRequest.password().equals(createRequest.confirmedPassword())){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Password does not match"
            );
        }

        User user = userMapper.fromUserCreateRequest(createRequest);
        user.setUuid(UUID.randomUUID().toString());
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setIsBlocked(false);
        user.setIsDeleted(false);

        List<Role> roles = new ArrayList<>();
        Role userRole = roleRepository.findByName("USER")
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Role user has not been found"
                        ));

        roles.add(userRole);
        user.setRoles(roles);

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
