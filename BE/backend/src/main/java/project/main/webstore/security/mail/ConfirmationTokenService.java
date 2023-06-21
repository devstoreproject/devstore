package project.main.webstore.security.mail;

import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import project.main.webstore.exception.BusinessLogicException;
import project.main.webstore.exception.CommonExceptionCode;

import javax.mail.MessagingException;
import java.util.Optional;

@Slf4j
@Service
public class ConfirmationTokenService {
    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final MailSenderService mailSenderService;

    public ConfirmationTokenService(ConfirmationTokenRepository confirmationTokenRepository, MailSenderService mailSenderService) {
        this.confirmationTokenRepository = confirmationTokenRepository;
        this.mailSenderService = mailSenderService;
    }

    //이메일 인증 토큰 생성 및 메일 전송
    public void createEmailConfirmationToken(Long id, String receiverEmail) throws MessagingException {

        ConfirmationToken confirmationToken = ConfirmationToken.createEmailConfirmationToken(id);
        confirmationTokenRepository.save(confirmationToken);

        String link = "/users/confirm-email?token=" + confirmationToken.getId();
        mailSenderService.sendEmail(receiverEmail, link);
    }

    public ConfirmationToken findByIdAndExpired(String confirmationTokenId) {
        Optional<ConfirmationToken> confirmationToken = confirmationTokenRepository.findByIdAndExpired(confirmationTokenId, false);
        return confirmationToken.orElseThrow(() -> new BusinessLogicException(CommonExceptionCode.EMAIL_TOKEN_EXPIRED));
    }

    public void useToken(ConfirmationToken token) {
        token.setExpired(true);
        confirmationTokenRepository.save(token);
    }
}
