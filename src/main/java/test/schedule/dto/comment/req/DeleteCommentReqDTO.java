package test.schedule.dto.comment.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeleteCommentReqDTO {
    @NotNull
    private Long id;
}
