package test.schedule.exception;


public class NotFoundException extends RuntimeException{
    public NotFoundException(String message) {
        super(message);
    }
    public static NotFoundException of(String text) {
        return new NotFoundException(text + " 찾을 수 없습니다.");
    }
}
