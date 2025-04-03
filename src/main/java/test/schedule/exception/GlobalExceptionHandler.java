package test.schedule.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    // 로그인 (이메일 + 비밀번호)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> IllegalArgumentException(IllegalArgumentException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("errorCode", "NOT_MATCHING");
        response.put("message", ex.getMessage());
        return ResponseEntity.badRequest().body(response);
    }
    // Valid
    // 프론트에서 리턴될텐데 에러코드가 필요할까 상태코드만으로 충분하지 않을지
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> MethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();

        Map<String, Object> response = new HashMap<>();
        response.put("errorCode", "VALIDATION_ERROR");

        List<Map<String, String>> errors = new ArrayList<>();
        for (FieldError error : fieldErrors) {
            Map<String, String> errorDetails = new HashMap<>();
            errorDetails.put("field", error.getField());
            errorDetails.put("rejectedValue", String.valueOf(error.getRejectedValue()));
            errorDetails.put("message", error.getDefaultMessage());
            errors.add(errorDetails);
        }

        response.put("errors", errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
    // NotFound 동적으로 처리함
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handleNotFound(NotFoundException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("errorCode", "NOT_FOUND");
        response.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
    // 권한
    @ExceptionHandler(UnauthorizedAccessException.class)
    public ResponseEntity<?> handleUnauthorized(UnauthorizedAccessException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("errorCode", "UNAUTHORIZED");
        response.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }
    @ExceptionHandler(DuplicateException.class) // 중복 이메일
    public ResponseEntity<?> handleDuplicateEmailException(DuplicateException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("errorCode", "DUPLICATED");
        response.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }


}
