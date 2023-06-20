package project.main.webstore.enums;

import lombok.Getter;
import project.main.webstore.dto.CustomCode;

@Getter
public enum ResponseCode implements CustomCode {

    CREATED, OK;

    private String code;
    private String message;

}
