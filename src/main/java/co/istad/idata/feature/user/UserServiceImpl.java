package co.istad.idata.feature.user;

import co.istad.idata.base.BasedMessage;
import co.istad.idata.domains.Role;
import co.istad.idata.domains.User;
import co.istad.idata.feature.mail.MailService;
import co.istad.idata.feature.user.dto.UpdatePasswordRequest;
import co.istad.idata.feature.user.registration.RegistrationRequest;
import co.istad.idata.feature.user.dto.UserResponse;
import co.istad.idata.feature.user.dto.UserUpdateRequest;
import co.istad.idata.feature.user.registration.token.VerificationToken;
import co.istad.idata.feature.user.registration.token.VerificationTokenRepository;
import co.istad.idata.mapper.UserMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final VerificationTokenRepository verificationTokenRepository;
    private final UserMapper userMapper;
    private final MailService mailService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponse registerUser(RegistrationRequest createRequest) {

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
        user.setPassword(passwordEncoder.encode(user.getPassword()));
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

    @Transactional
    @Override
    public BasedMessage disableByUuid(String uuid) {

        userRepository.disableByUuid(uuid);

        return BasedMessage.builder()
                .message("User has been disabled")
                .build();
    }

    @Transactional
    @Override
    public BasedMessage enableByUuid(String uuid) {

        userRepository.enableByUuid(uuid);

        return BasedMessage.builder()
                .message("User has been enabled")
                .build();
    }

    @Transactional
    @Override
    public BasedMessage blockByUuid(String uuid){

        userRepository.blockByUuid(uuid);

        return BasedMessage.builder()
                .message("User has been blocked")
                .build();

    }
    @Override
    public void saveUserVerificationToken(User theUser, String token) {

        var verificationToken = new VerificationToken(token, theUser);

        verificationTokenRepository.save(verificationToken);

    }

    @Override
    public BasedMessage updatePassword(UpdatePasswordRequest passwordRequest) {

        if (!userRepository.existsByPassword(passwordRequest.oldPassword())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Wrong password! Please try again."
            );
        }

        if (!passwordRequest.newPassword().equals(passwordRequest.confirmPassword())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Password does not match, Please try again."
            );
        }

        User user = userRepository.findAll()
                .stream()
                .filter(user1 -> user1.getPassword().equals(passwordRequest.oldPassword()))
                .findFirst().orElseThrow();

        user.setPassword(passwordEncoder.encode(passwordRequest.newPassword()));

        userRepository.save(user);

        return BasedMessage.builder()
                .message("Password has been changed successfully.")
                .build();
    }
}
