package co.istad.idata.event.listener;

import co.istad.idata.domains.User;
import co.istad.idata.event.RegistrationCompleteEvent;
import co.istad.idata.feature.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class RegistrationCompleteEventListener
        implements ApplicationListener<RegistrationCompleteEvent> {

    private final UserService userService;

    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {

        // 1. Get the new registered user
        User theUser = event.getUser();

        // 2. Create a verification token for the user
        String verificationToken = UUID.randomUUID().toString();

        // 3. Save the verification token for the user
        userService.saveUserVerificationToken(theUser, verificationToken);

        // 4. Build the verification url to be sent to the user
        String url = event.getApplicationUrl() + "/register/verify-email?token=" + verificationToken;

        // 5. Send the email
        log.info("Click the link to verify your email: {}", url);

    }
}
