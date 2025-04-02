package test.schedule.dto.schedule.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class PostScheduleReqDTO {
    @NotBlank
    @Size(min = 1, max = 10)
    private String title;
    @NotBlank
    @Size(min = 1, max = 100)
    private String content;
}
