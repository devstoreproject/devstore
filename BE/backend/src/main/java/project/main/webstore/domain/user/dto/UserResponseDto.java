package project.main.webstore.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import project.main.webstore.domain.user.enums.Grade;
import project.main.webstore.domain.user.enums.UserRole;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class UserResponseDto {
        private Long id;
        private String email;
        private String nickName;
        private String profileImage;
        private LocalDateTime lastConnectedDate;
        private int mileage;
        private Grade grade;
        private UserRole userRole;
}
