package co.istad.idata.feature.user;

import co.istad.idata.base.BasedMessage;
import co.istad.idata.domains.User;
import co.istad.idata.feature.user.dto.UpdatePasswordRequest;
import co.istad.idata.feature.user.registration.RegistrationRequest;
import co.istad.idata.feature.user.dto.UserResponse;
import co.istad.idata.feature.user.dto.UserUpdateRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    List<UserResponse> findAll(){
        return userService.findAll();
    }

    @GetMapping("/{id}")
    UserResponse findById(@PathVariable Long id){
        return userService.findUserById(id);
    }

//    @PostMapping
//    UserResponse createUser(@RequestBody @Valid RegistrationRequest createRequest){
//        return userService.registerUser(createRequest);
//    }

    @PutMapping("/{id}")
    UserResponse updateUser(@PathVariable Long id,
                            @RequestBody @Valid UserUpdateRequest updateRequest){
        return userService.updateUserById(id, updateRequest);
    }

    @DeleteMapping("/{id}")
    void deleteUser(@PathVariable Long id){
        userService.deleteUserById(id);
    }

    @PutMapping("/{uuid}/disable-user")
    BasedMessage disableByUuid(@PathVariable String uuid){
        return userService.disableByUuid(uuid);
    }

    @PutMapping("/{uuid}/enable-user")
    BasedMessage enableByUuid(@PathVariable String uuid){
        return userService.enableByUuid(uuid);
    }

    @PutMapping("/{uuid}/block-user")
    BasedMessage blockByUuid(@PathVariable String uuid){
        return userService.blockByUuid(uuid);
    }

    @PutMapping("/change-password")
    BasedMessage updatePassword(@RequestBody @Valid UpdatePasswordRequest request){
        return userService.updatePassword(request);
    }


}
