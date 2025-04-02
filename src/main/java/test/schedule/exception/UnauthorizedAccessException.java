package test.schedule.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;

public class UnauthorizedAccessException extends RuntimeException{
    public UnauthorizedAccessException() {
        super("권한이 없습니다.");
    }
}
