package co.istad.idata.feature.user;

import co.istad.idata.feature.user.dto.UserCreateRequest;
import co.istad.idata.feature.user.dto.UserResponse;
import co.istad.idata.feature.user.dto.UserUpdateRequest;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    List<UserResponse> findAll(){
        return userService.findAll();
    }

    @GetMapping("/id")
    UserResponse findById(@PathVariable Long id){
        return userService.findUserById(id);
    }

    @PostMapping
    UserResponse createUser(@RequestBody @Valid UserCreateRequest createRequest){
        return userService.createUser(createRequest);
    }

    @PutMapping("/id")
    UserResponse updateUser(@PathVariable Long id,
                            @RequestBody @Valid UserUpdateRequest updateRequest){
        return userService.updateUserById(id, updateRequest);
    }

    @DeleteMapping("/id")
    void deleteUser(@PathVariable Long id){
        userService.deleteUserById(id);
    }

}
