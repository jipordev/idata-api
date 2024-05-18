package co.istad.idata.feature.user;

import co.istad.idata.feature.user.dto.UserCreateRequest;
import co.istad.idata.feature.user.dto.UserResponse;
import co.istad.idata.feature.user.dto.UserUpdateRequest;

import java.util.List;

public interface UserService {

    // Create a new user
    UserResponse createUser(UserCreateRequest createRequest);

    // Update a user
    UserResponse updateUserById(Long id, UserUpdateRequest updateRequest);

    // Find all users
    List<UserResponse> findAll();

    // Find user by id
    UserResponse findUserById(Long id);

    // Delete a user
    void deleteUserById(Long id);

}
