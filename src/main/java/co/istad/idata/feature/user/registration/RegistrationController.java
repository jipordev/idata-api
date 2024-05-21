package co.istad.idata.feature.user.registration;

import co.istad.idata.domains.User;
import co.istad.idata.event.RegistrationCompleteEvent;
import co.istad.idata.feature.user.UserService;
import co.istad.idata.feature.user.dto.UserResponse;
import co.istad.idata.mapper.UserMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.aspectj.apache.bcel.classfile.Module;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/register")
public class RegistrationController {

    private final UserService userService;
    private final UserMapper userMapper;
    private final ApplicationEventPublisher publisher;

    @PostMapping
    public String registerUser(@RequestBody @Valid RegistrationRequest registrationRequest, final HttpServletRequest request){

        UserResponse userResponse = userService.registerUser(registrationRequest);

        User user = userMapper.fromUserResponse(userResponse);

        // publish registration event
        publisher.publishEvent(new RegistrationCompleteEvent(user, applicationUrl(request)));

        return "Registration success! Please check your email to complete your registration";

    }

    public String applicationUrl(HttpServletRequest request) {

        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();

    }

}
