package project.main.webstore.email.event;


import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.mail.MailSendException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import project.main.webstore.domain.users.entity.User;
import project.main.webstore.domain.users.service.UserService;
import project.main.webstore.email.EmailSender;
import project.main.webstore.redis.RedisUtils;

import java.util.UUID;

@EnableAsync
@Configuration
@Component
@Slf4j
@RequiredArgsConstructor
public class UserRegistrationEventListener {
    private final EmailSender emailSender;
    private final UserService userService;
    private final RedisUtils redisUtils;
    @Value("${mail.subject.member.registration}")
    private String subject;
    @Value("${mail.template.name.member.join}")
    private String templateName;
    @Value("${spring.redis.host}")
    private String url;

    @SneakyThrows
    @Async
    @EventListener
    public void listen(UserRegistrationApplicationEvent event) {
        try {
            log.info("### User email = {}",event.getUser().getEmail());
            String[] to = new String[]{event.getUser().getEmail()};
            String key = UUID.randomUUID().toString();

            String message = event.getUser().getEmail() + "http://"+url+":8080/api/users/auth-mail?" +"key="+key;

            //레디스에 인증 키 값 저장 -> 인증 키 유효 기간 10분 제한 -> 10분 이후 에러 발생
            redisUtils.set(key,event.getUser().getEmail(),10);

            emailSender.sendEmail(to, subject, message, templateName);
        } catch (MailSendException e) {
            log.error("### MailSend Error ###");
            e.printStackTrace();
            User user = event.getUser();
            userService.deleteUser(user.getId());
        }
    }
}
