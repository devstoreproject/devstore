package project.main.webstore.domain.users.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import project.main.webstore.audit.Auditable;
import project.main.webstore.domain.users.enums.Grade;
import project.main.webstore.domain.users.enums.ProviderId;
import project.main.webstore.domain.users.enums.UserRole;
import project.main.webstore.valueObject.Address;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
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

    @Embedded
    private Address address;

    @Enumerated(STRING)
    private Grade grade = Grade.NORMAL;
    @Enumerated(STRING)
    private ProviderId providerId = ProviderId.JWT;
    @Enumerated(STRING)
    private UserRole userRole = UserRole.CLIENT;

    public User(String nickName,String profileImage ,String password, String email, int mileage, Address address, Grade grade, ProviderId providerId, UserRole userRole) {
        this.nickName = nickName;
        this.profileImage = profileImage;
        this.password = password;
        this.email = email;
        this.lastConnectedDate = LocalDateTime.now();
        this.mileage = mileage;
        this.address = address;
        this.grade = grade;
        this.providerId = providerId;
        this.userRole = userRole;
    }
}
