package test.schedule.dto.schedule.res;

import lombok.Getter;
import lombok.Setter;
import test.schedule.entity.Schedule;

import java.time.LocalDateTime;

@Getter
@Setter
public class GetScheduleResDTO {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String name;
    private Integer commentCount;

    private GetScheduleResDTO(Schedule schedule) {
        this.id = schedule.getId();
        this.title = schedule.getTitle();
        this.content = schedule.getContent();
        this.createdAt = schedule.getCreatedAt();
        this.updatedAt = schedule.getUpdatedAt();
        this.name = schedule.getUser().getName();
    }

    public static GetScheduleResDTO from(Schedule schedule, Integer commentCount) {
        GetScheduleResDTO dto = new GetScheduleResDTO(schedule);
        dto.commentCount = commentCount;
        return dto;
    }

    public static GetScheduleResDTO from(Schedule schedule) {
        return new GetScheduleResDTO(schedule);
    }
}
