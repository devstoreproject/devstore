package project.main.webstore.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import project.main.webstore.dto.CustomCode;

@Getter
@AllArgsConstructor
public enum ResponseCode implements CustomCode {

    CREATED("C201","생성 완료"), OK("C200","작업 완료");

    private String code;
    private String message;

}
