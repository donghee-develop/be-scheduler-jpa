package test.schedule.dto.comment.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class PutCommentReqDTO {
    @NotNull
    private Long id;
    @Size(min = 1, max = 100)
    private String content;
}
