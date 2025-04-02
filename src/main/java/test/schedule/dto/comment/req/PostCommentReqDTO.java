package test.schedule.dto.comment.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostCommentReqDTO {
    @NotNull
    private Long id;
    @NotBlank
    @Size(min = 1, max = 100)
    private String content;

}
