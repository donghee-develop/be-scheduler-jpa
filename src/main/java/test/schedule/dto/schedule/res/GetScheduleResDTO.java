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

    public static GetScheduleResDTO from(Schedule schedule) {
        GetScheduleResDTO dto = new GetScheduleResDTO();
        dto.setId(schedule.getId());
        dto.setTitle(schedule.getTitle());
        dto.setContent(schedule.getContent());
        dto.setCreatedAt(schedule.getCreatedAt());
        dto.setUpdatedAt(schedule.getUpdatedAt());
        dto.setName(schedule.getUser().getName());
        return dto;
    }
}
