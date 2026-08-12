package com.essel.portfolio.service;

import com.essel.portfolio.dto.ContactRequest;
import com.essel.portfolio.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    @Value("${app.mail.admin-email}")
    private String toAddress;

    @Value("${spring.mail.username}")
    private String fromAddress;

    @Override
    public void sendContactEmail(ContactRequest request) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            Context context = new Context();
            context.setVariable("name", request.getName());
            context.setVariable("email", request.getEmail());
            context.setVariable("projectType", request.getProjectType());
            context.setVariable("budgetRange", request.getBudgetRange());
            context.setVariable("message", request.getMessage());

            String htmlBody = templateEngine.process("contact-email", context);

            helper.setTo(toAddress);
            helper.setFrom(fromAddress);
            helper.setReplyTo(request.getEmail());
            helper.setSubject("New Portfolio Contact — " + request.getName());
            helper.setText(htmlBody, true);

            mailSender.send(mimeMessage);
            log.info("Contact email sent for submission from {}", request.getEmail());

        } catch (MessagingException | MailException ex) {
            log.error("Failed to send contact email", ex);
            throw new RuntimeException("Failed to send contact email", ex);
        }
    }
}
