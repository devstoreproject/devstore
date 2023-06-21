package project.main.webstore.security.mail;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ConfirmationToken {
    private static final long EMAIL_TOKEN_EXPIRATION_TIME_VALUE = 5L;

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(length = 36)
    private String tokenId;
    private Boolean expired;
    private Long id;

    public static ConfirmationToken createEmailConfirmationToken(Long id){
        ConfirmationToken confirmationToken = new ConfirmationToken();
        confirmationToken.id = id;
        confirmationToken.expired = false;
        return confirmationToken;
    }
}
