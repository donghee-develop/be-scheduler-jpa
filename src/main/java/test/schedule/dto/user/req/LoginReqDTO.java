package test.schedule.dto.user.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class LoginReqDTO {
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$",
            message = "올바른 이메일 양식을 작성해주세요"
    )
    private String email;
    @NotBlank
    @Size(min = 6, max = 30)
    private String password;
}
