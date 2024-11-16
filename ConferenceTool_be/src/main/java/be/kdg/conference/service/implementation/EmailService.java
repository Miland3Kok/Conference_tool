package be.kdg.conference.service.implementation;

import be.kdg.conference.config.EmailConfig;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.Properties;


@Service
@Log4j2
public class EmailService {
    private final Session session;
    private final EmailConfig emailConfig;

    @Autowired
    public EmailService(EmailConfig emailConfig) {
        this.emailConfig = emailConfig;

        Properties prop = new Properties();
        prop.put("mail.smtp.host", emailConfig.getHost());
        prop.put("mail.smtp.auth", emailConfig.getAuth());
        prop.put("mail.smtp.starttls.enable", emailConfig.getStarttls());
        prop.put("mail.protocol", emailConfig.getProtocol());
        prop.put("mail.smtp.port", emailConfig.getPort());
        prop.put("mail.smtp.ssl.trust", emailConfig.getHost());
        prop.put("mail.smtp.username", emailConfig.getUsername());
        prop.put("mail.smtp.password", emailConfig.getPassword());
        this.session = Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailConfig.getUsername(), emailConfig.getPassword());
            }
        });

    }

    public void sendPassword(String senderName, String emailReceiver, String password) throws MessagingException {
        String conferenceWebsiteUrl = "http://localhost:4200";

        Message message = new MimeMessage(session);
        message.setRecipient( Message.RecipientType.TO , new  InternetAddress(emailReceiver));
        message.setFrom( new  InternetAddress(emailConfig.getUsername()));
        message.setSubject("Axxes Conference Tool Invite");
        message.setSentDate(Date.from(Instant.now()));


        String htmlContent = "<html>"
                + "<head>" + "</head>"
                + "<body>"
                + "<div id=\"container\" style=\"width: 100%; max-width: 600px; margin: 0 auto; text-align: center; background-color: #f5f5f5; padding: 20px; border-radius: 10px;\">"
                + "<p style=\"font-size: 18px;\">Hey " + senderName + ", welkom op het platform van onze conferentie!</p>"
                + "<p style=\"font-size: 18px;\">Hier is je wachtwoord: <strong>" + password + "</strong></p>"
                + "<p style=\"font-size: 18px;\">Bezoek onze conferentiewebsite <a href=\"" + conferenceWebsiteUrl + "\">hier</a>.</p>"
                + "</div>"
                + "</body>"
                + "</html>";

        message.setContent(htmlContent, "text/html; charset=utf-8");

        try{
            Transport.send(message);
        } catch (MessagingException e) {
            log.error("Error sending email: " + e.getMessage(), e);
            throw new RuntimeException("Error sending email", e);
        }
    }
}
