package test.schedule.dto.schedule.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class PutScheduleReqDTO {
    @NotNull
    private Long id;
    @NotBlank
    @Size(min = 1, max = 10)
    private String title;
    @NotBlank
    @Size(min = 1, max = 100)
    private String content;
}
