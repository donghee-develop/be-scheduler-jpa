package test.schedule.dto.schedule.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class DeleteScheduleReqDTO {
    @NotNull
    private Long id;
}
