package test.schedule.dto.comment.res;

import lombok.Getter;
import lombok.Setter;
import test.schedule.entity.Comment;

import java.time.LocalDateTime;
@Getter
@Setter
public class PostCommentResDTO {
    private Long id;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String name;

    public static PostCommentResDTO from(Comment comment) {
        PostCommentResDTO dto = new PostCommentResDTO();
        dto.setId(comment.getId());
        dto.setContent(comment.getContent());
        dto.setCreatedAt(comment.getCreatedAt());
        dto.setUpdatedAt(comment.getUpdatedAt());
        dto.setName(comment.getUser().getName());
        return dto;
    }
}
