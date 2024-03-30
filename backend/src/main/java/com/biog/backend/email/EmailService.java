package com.biog.backend.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmailService {

  private final JavaMailSender javaMailSender;

  public void sendEmail(String to, String subject, String text)
    throws MessagingException {
    MimeMessage message = javaMailSender.createMimeMessage();

    message.setFrom(new InternetAddress("noreply@ims.ensias.com"));
    message.setRecipients(
      MimeMessage.RecipientType.TO,
      InternetAddress.parse(to)
    );
    message.setSubject(subject);

    String htmlContent = "<h1>Internship Management</h1><p>" + text + "</p>";
    message.setContent(htmlContent, "text/html");
    javaMailSender.send(message);
  }
}
