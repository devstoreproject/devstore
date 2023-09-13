package project.main.webstore.domain.qna.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum QnaStatus {
    REGISTER,ANSWER_COMPLETE
    ;

    public static QnaStatus of (String status){
        for (QnaStatus value : values()) {
            if(value.name().equals(status)){
                return  value;
            }
        }
        return null;
    }
}
