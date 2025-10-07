package Utill;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Properties;

public class MailService {

    private final String email = "abdullahjamshaid61@gmail.com"; // sender email
    private final String password = "wfyncfhfmlvoxkxo";   // app-specific password (NOT Gmail password!)

    private final Session session;

    public MailService() {
        // SMTP properties
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true"); // TLS
        props.put("mail.smtp.host", "smtp.gmail.com");  // Gmail SMTP
        props.put("mail.smtp.port", "587");

        // Create session with authentication
        session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(email, password);
            }

        });
        session.setDebug(true);
    }

    /**
     * Send a simple email
     */
    public void sendMail(String toEmail, String subject, String body) throws MessagingException {
        // Create message
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(email));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
        message.setSubject(subject);
        message.setText(body);

        // Send message
        Transport.send(message);
    }

    /**
     * Send OTP email
     */
    public void sendOtp(String toEmail, String otp) throws MessagingException {
        String subject = "Your OTP Code";
        String body = "Your OTP is: " + otp + "\nIt will expire in 5 minutes.";
        sendMail(toEmail, subject, body);
    }
}
