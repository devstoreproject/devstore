package project.main.webstore.domain.user.entity;

import lombok.*;
import project.main.webstore.audit.Auditable;
import project.main.webstore.domain.user.enums.Grade;
import project.main.webstore.domain.user.enums.ProviderId;
import project.main.webstore.domain.user.enums.Status;
import project.main.webstore.domain.user.enums.UserRole;
import project.main.webstore.valueObject.Address;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
@Entity
@Builder
public class User extends Auditable {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(updatable = false)
    private Long id;
    private String nickName;
    private String profileImage;
    private String password;
    private String email;
    private LocalDateTime lastConnectedDate;
    private int mileage;
    private boolean emailVerified;

    @Embedded
    private Address address;

    @Enumerated(STRING)
    private Grade grade = Grade.NORMAL;
    @Enumerated(STRING)
    private ProviderId providerId = ProviderId.JWT;
    @Enumerated(STRING)
    private Status status = Status.ACTIVE;
    @Enumerated(STRING)
    private UserRole userRole = UserRole.CLIENT;


    public User(String nickName, String profileImage, String password, String email, int mileage, boolean emailVerified, Address address, Grade grade, ProviderId providerId, Status status, UserRole userRole) {
        this.nickName = nickName;
        this.profileImage = profileImage;
        this.password = password;
        this.email = email;
        this.lastConnectedDate = LocalDateTime.now();
        this.mileage = mileage;
        this.emailVerified = emailVerified;
        this.address = address;
        this.grade = grade;
        this.providerId = providerId;
        this.status = status;
        this.userRole = userRole;
    }
}
