package project.main.webstore.domain.users.enums;

import lombok.AllArgsConstructor;
import project.main.webstore.exception.BusinessLogicException;
import project.main.webstore.exception.CommonExceptionCode;

@AllArgsConstructor
public enum ProviderId {
    JWT,GOOGLE,KAKAO
    //OAuth 추가를 하고싶다면 이 곳에 추가해주세요
    ;

    public static ProviderId of(String providerId){
        for (ProviderId value : ProviderId.values()) {
            if(value.name().equals(providerId)){
                return value;
            }
        }
        throw new BusinessLogicException(CommonExceptionCode.PROVIDER_NOT_FOUND);
    }
}
