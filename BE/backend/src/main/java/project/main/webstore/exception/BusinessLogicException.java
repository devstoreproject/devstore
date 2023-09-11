package project.main.webstore.exception;

import lombok.Getter;

@Getter
public class BusinessLogicException extends RuntimeException {
    ExceptionCode exceptionCode;

    public BusinessLogicException(ExceptionCode exceptionCode) {
        super(exceptionCode.getMessage());
        this.exceptionCode = exceptionCode;
    }

    public int getHttpCode() {

        return this.exceptionCode.getHttpStatus().value();
    }

    public String getReason() {

        return this.exceptionCode.getMessage();
    }
}
