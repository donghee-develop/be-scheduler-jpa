package test.schedule.dto.user.req;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class PutUserReqDTO {
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$",
            message = "올바른 이메일 양식을 작성해주세요")
    private String email;
    @NotBlank
    @Size(min = 1, max =4)
    private String name;
    @NotBlank
    @Size(min = 6, max = 30)
    private String password;
}
