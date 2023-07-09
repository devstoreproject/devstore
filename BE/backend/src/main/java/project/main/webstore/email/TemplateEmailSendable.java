package project.main.webstore.email;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.internet.MimeMessage;

@Slf4j
@RequiredArgsConstructor
public class TemplateEmailSendable implements EmailSendable {
    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;
    private final Context context;

    @Override
    public void send(String[] to, String subject, String message, String templateName) throws InterruptedException {
        try {
            context.setVariable("message", message);

            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");

            String html = templateEngine.process(templateName, context);
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(html, true);

            javaMailSender.send(mimeMessage);
            log.info("Sent Template email!");
        } catch (Exception e) {
            log.error("email send error: ", e);
        }

    }
}
