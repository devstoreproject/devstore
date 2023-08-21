package project.main.webstore.domain.users.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.main.webstore.domain.qna.entity.Question;
import project.main.webstore.domain.users.entity.User;
import project.main.webstore.domain.users.exception.UserExceptionCode;
import project.main.webstore.domain.users.repository.UserRepository;
import project.main.webstore.exception.BusinessLogicException;

@Service
@RequiredArgsConstructor
public class UserValidService {
    private final UserRepository repository;
    public User validByMail(String email) {
        return repository.findByEmail(email).orElseThrow(()-> new BusinessLogicException(UserExceptionCode.USER_NOT_FOUND));
    }

    public User validUser(Long userId) {
        return repository.findById(userId).orElseThrow(()-> new BusinessLogicException(UserExceptionCode.USER_NOT_FOUND));
    }

    public User validUserAllInfo(Long userId){
        return repository.findAllInfoById(userId).orElseThrow(()-> new BusinessLogicException(UserExceptionCode.USER_NOT_FOUND));
    }

    public void validUserIdSame(Long userId, Question find) {
        if(!find.getUser().getId().equals(userId)){
            throw new BusinessLogicException(UserExceptionCode.USER_INFO_MISMATCH);
        }
    }
}
