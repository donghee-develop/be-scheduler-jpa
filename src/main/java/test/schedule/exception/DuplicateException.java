package test.schedule.exception;

public class DuplicateException extends RuntimeException{
    public DuplicateException(String message) {
        super(message);
    }
    public static DuplicateException of(String text) {
        return new DuplicateException(text + " 중복입니다.");
    }
}
