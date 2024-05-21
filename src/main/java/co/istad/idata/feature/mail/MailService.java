package co.istad.idata.feature.mail;

public interface MailService {

    void sendResetTokenEmail(String to, String resetUrl);

}
