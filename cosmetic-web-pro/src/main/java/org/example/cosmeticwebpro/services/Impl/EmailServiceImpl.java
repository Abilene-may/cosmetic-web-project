package org.example.cosmeticwebpro.services.Impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.example.cosmeticwebpro.services.EmailService;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
  private final JavaMailSender javaMailSender;
  private final TemplateEngine templateEngine;


  @Override
  public void sendEmailWithHtmlTemplate(String to, String subject, String templateName,
      Context context) {
    MimeMessage mimeMessage = javaMailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");

    try {
      helper.setTo(to);
      helper.setSubject(subject);
      String htmlContent = templateEngine.process(templateName, context);
      helper.setText(htmlContent, true);
      javaMailSender.send(mimeMessage);
    } catch (MailSendException e) {
      System.out.println(e);
    } catch (MessagingException e) {
      System.out.println("Nothing!");

    }
  }
}
