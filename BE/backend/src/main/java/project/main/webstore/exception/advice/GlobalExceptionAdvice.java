package project.main.webstore.exception.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import project.main.webstore.exception.BusinessLogicException;
import project.main.webstore.exception.ErrorResponse;

import javax.validation.ConstraintViolationException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionAdvice {

    /*
    * 로직에러
    * */
    @ExceptionHandler
    public ResponseEntity handleBusinessException(BusinessLogicException e) {
        log.error("BusinessLogic Exception Error : {}", e.getMessage());
        final ErrorResponse errorResponse = ErrorResponse.of(e.getExceptionCode());
        return new ResponseEntity(errorResponse, e.getExceptionCode().getHttpStatus());
    }

    /*
    * Dto 검증오류
    * */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("MethodArgumentNotValidException : {}", e.getMessage());
        final ErrorResponse errorResponse = ErrorResponse.of(e.getBindingResult());
        return errorResponse;
    }

    /*
    * Controller 검증 오류
    * */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleConstraintViolationException(ConstraintViolationException e) {
        log.error("ConstraintViolationException : {}", e.getMessage());
        final ErrorResponse errorResponse = ErrorResponse.of(e.getConstraintViolations());
        return errorResponse;
    }

    /*
    * HTTP 메서드 오류
    * */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ErrorResponse handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error("HttpRequestMethodNotSupportedException : {}", e.getMessage());
        final ErrorResponse errorResponse = ErrorResponse.of(HttpStatus.METHOD_NOT_ALLOWED, e.getMessage());
        return errorResponse;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleException(MethodArgumentTypeMismatchException e) {
        log.error("MethodArgumentTypeMismatchException : ", e);
        log.error("MethodArgumentTypeMismatchException : {}", e.getMessage());
        final ErrorResponse errorResponse = ErrorResponse.of(HttpStatus.BAD_REQUEST, e.getMessage());
        return errorResponse;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleException(MissingServletRequestPartException e) {
        log.error("MissingServletRequestPartException : {}", e.getMessage());
        final ErrorResponse errorResponse = ErrorResponse.of(HttpStatus.NOT_FOUND, e.getMessage());
        return errorResponse;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleException(MissingServletRequestParameterException e) {
        log.error("MissingServletRequestParameterException : {}", e.getMessage());
        final ErrorResponse errorResponse = ErrorResponse.of(HttpStatus.BAD_REQUEST, e.getMessage());
        return errorResponse;
    }

//    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleException(Exception e) {
        log.error("Exception : {}", e.getMessage());
        final ErrorResponse errorResponse = ErrorResponse.of(HttpStatus.INTERNAL_SERVER_ERROR);
        return errorResponse;
    }
}