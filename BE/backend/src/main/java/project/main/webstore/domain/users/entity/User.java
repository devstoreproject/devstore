package project.main.webstore.domain.users.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.main.webstore.audit.Auditable;
import project.main.webstore.domain.cart.entity.Cart;
import project.main.webstore.domain.item.entity.PickedItem;
import project.main.webstore.domain.users.dto.UserPatchRequestDto;
import project.main.webstore.domain.users.dto.UserPostRequestDto;
import project.main.webstore.domain.users.enums.Grade;
import project.main.webstore.domain.users.enums.ProviderId;
import project.main.webstore.domain.users.enums.UserRole;
import project.main.webstore.domain.users.enums.UserStatus;

import javax.persistence.*;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;
import static project.main.webstore.domain.users.enums.ProviderId.JWT;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Table(name = "USERS")
@Entity
@Setter
public class User extends Auditable implements Principal {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(updatable = false)
    private Long id;
    private String nickName;
    private String userName;
    private String profileImage;
    private String password;
    private String email;
    private LocalDateTime lastConnectedDate;
    private int mileage;

    private String phone;

    @Enumerated(STRING)
    private Grade grade = Grade.NORMAL;
    @Enumerated(STRING)
    private ProviderId providerId = JWT;
    @Enumerated(STRING)
    private UserRole userRole = UserRole.CLIENT;
    @Enumerated(STRING)
    private UserStatus userStatus = UserStatus.TMP;

    @OneToMany(mappedBy = "user")
    private List<PickedItem> pickedItemList = new ArrayList<>();
    @OneToMany(mappedBy = "user")
    private List<ShippingInfo> shippingInfoList = new ArrayList<>();
    @OneToOne(fetch = LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "CART_ID")
    private Cart cart;

    @Override
    public String getName() {
        return getEmail();
    }

    public ShippingInfo getShippingInfo(Long infoId){
        for (ShippingInfo info: shippingInfoList) {
            if(info.getInfoId().equals(infoId)){
                return info;
            }
        }
        return null;
    }

    public User(String nickName, String profileImage, String password, String email, int mileage, String phone, Grade grade, ProviderId providerId, UserRole userRole) {
        this.nickName = nickName;
        this.profileImage = profileImage;
        this.password = password;
        this.email = email;
        this.lastConnectedDate = LocalDateTime.now();
        this.mileage = mileage;
        this.phone = phone;
        this.grade = grade;
        this.providerId = providerId;
        this.userRole = userRole;
    }

    public User(Long id) {
        this.id = id;
    }

    protected User(Long id, String nickName, String password, String email, UserRole userRole, UserStatus userStatus) {
        this.id = id;
        this.nickName = nickName;
        this.password = password;
        this.email = email;
        this.userRole = userRole;
        this.userStatus = userStatus;
    }

    @Builder(builderMethodName = "stubBuilder")
    public User(Long id, String nickName, String userName, String profileImage, String password, String email, int mileage, String phone, List<PickedItem> pickedItemList, Cart cart) {
        this.id = id;
        this.nickName = nickName;
        this.userName = userName;
        this.profileImage = profileImage;
        this.password = password;
        this.email = email;
        this.lastConnectedDate = LocalDateTime.now();
        this.mileage = mileage;
        this.phone = phone;
        this.grade = Grade.NORMAL;
        this.providerId = JWT;
        this.userRole = UserRole.CLIENT;
        this.userStatus = UserStatus.ACTIVE;
        this.pickedItemList = pickedItemList;
        this.cart = cart;
    }

    @Builder(builderMethodName = "jwtBuilder")
    public User(UserPostRequestDto post) {
        this.nickName = post.getNickname();
        this.password = post.getPassword();
        this.email = post.getEmail();
        this.lastConnectedDate = LocalDateTime.now();
        this.phone = post.getPhone();
        this.grade = Grade.NORMAL;
        this.providerId = JWT;
        this.userRole = UserRole.CLIENT;
        this.userStatus = UserStatus.TMP;
        this.userName = post.getUserName();
    }
    @Builder(builderMethodName = "patchBuilder")
    public User(UserPatchRequestDto patch) {
        this.nickName = patch.getNickname();
        this.password = patch.getPassword();
        this.lastConnectedDate = LocalDateTime.now();
        this.phone = patch.getPhone();
        this.grade = Grade.NORMAL;
        this.providerId = JWT;
        this.userRole = UserRole.CLIENT;
        this.userStatus = UserStatus.TMP;
    }


}
