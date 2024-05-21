package co.istad.idata.feature.mail;

import co.istad.idata.feature.mail.dto.MailRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService{

    private final JavaMailSender javaMailSender;

    @Override
    public void sendResetTokenEmail(String to, String resetUrl) {
        MailRequest.builder()
                .to(to)
                .subject("Password reset request")
                .text("To reset your password, click the link below:\n" + resetUrl)
                .build();
    }

}
