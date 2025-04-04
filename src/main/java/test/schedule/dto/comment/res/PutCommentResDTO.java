package test.schedule.dto.comment.res;

import lombok.Getter;
import lombok.Setter;
import test.schedule.entity.Comment;

import java.time.LocalDateTime;

@Getter
@Setter
public class PutCommentResDTO {
    private Long id;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String name;

    public static PutCommentResDTO from(Comment comment) {
        PutCommentResDTO dto = new PutCommentResDTO();
        dto.setId(comment.getId());
        dto.setContent(comment.getContent());
        dto.setCreatedAt(comment.getCreatedAt());
        dto.setUpdatedAt(comment.getUpdatedAt());
        dto.setName(comment.getUser().getName());
        return dto;
    }
}
