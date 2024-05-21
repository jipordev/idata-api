package co.istad.idata.feature.user;

import co.istad.idata.base.BasedMessage;
import co.istad.idata.domains.User;
import co.istad.idata.feature.user.dto.UpdatePasswordRequest;
import co.istad.idata.feature.user.registration.RegistrationRequest;
import co.istad.idata.feature.user.dto.UserResponse;
import co.istad.idata.feature.user.dto.UserUpdateRequest;

import java.util.List;

public interface UserService {

    // Create a new user
    UserResponse registerUser(RegistrationRequest createRequest);

    // Update a user
    UserResponse updateUserById(Long id, UserUpdateRequest updateRequest);

    // Find all users
    List<UserResponse> findAll();

    // Find user by id
    UserResponse findUserById(Long id);

    // Delete a user
    void deleteUserById(Long id);

    // Update user password
    BasedMessage updatePassword(UpdatePasswordRequest passwordRequest);

    // (disable, enable, block) a user by uuid
    BasedMessage disableByUuid(String uuid);
    BasedMessage enableByUuid(String uuid);
    BasedMessage blockByUuid(String uuid);

    void saveUserVerificationToken(User theUser, String verificationToken);
}
