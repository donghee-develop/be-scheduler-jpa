package test.schedule.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import test.schedule.dto.schedule.req.DeleteScheduleReqDTO;
import test.schedule.dto.schedule.res.GetScheduleResDTO;
import test.schedule.dto.schedule.req.PostScheduleReqDTO;
import test.schedule.dto.schedule.req.PutScheduleReqDTO;
import test.schedule.entity.Schedule;
import test.schedule.entity.User;
import test.schedule.exception.NotFoundException;
import test.schedule.exception.UnauthorizedAccessException;
import test.schedule.repository.CommentRepository;
import test.schedule.repository.ScheduleRepository;
import test.schedule.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    public Page<GetScheduleResDTO> searchSchedules(String keyword, Pageable pageable) {
        Page<Schedule> schedules = scheduleRepository.findByContentContaining(keyword, pageable);

        return schedules.map(schedule -> {
            int commentCount = commentRepository.findCommentsCountByScheduleId(schedule.getId());
            return GetScheduleResDTO.from(schedule, commentCount);
        });

//        return scheduleRepository.findByContentContaining(keyword, pageable)
//                .map(GetScheduleResDTO::from);
    }

    @Transactional
    public void saveSchedule(PostScheduleReqDTO postScheduleReqDTO, Long userId) {
        // 예외는 안뜸 세션에서 처리함
        User user = userRepository.findById(userId)
                .orElseThrow(() -> NotFoundException.of("유저"));

        Schedule schedule = new Schedule();
        schedule.setTitle(postScheduleReqDTO.getTitle());
        schedule.setContent(postScheduleReqDTO.getContent());
        schedule.setUser(user);
        scheduleRepository.save(schedule);
    }

    public Schedule getScheduleById(Long id) {
        return scheduleRepository.findById(id)
                .orElseThrow(() -> NotFoundException.of("스케줄"));
    }

    @Transactional
    public void updateSchedule(PutScheduleReqDTO putScheduleReqDTO, Long userId) {
        Schedule schedule = scheduleRepository.findById(putScheduleReqDTO.getId())
                .orElseThrow(() -> NotFoundException.of("스케줄"));
        if (!schedule.getUser().getUserId().equals(userId)) {
            throw new UnauthorizedAccessException();
        }
        schedule.setTitle(putScheduleReqDTO.getTitle());
        schedule.setContent(putScheduleReqDTO.getContent());
    }

    @Transactional
    public void deleteSchedule(DeleteScheduleReqDTO deleteScheduleReqDTO, Long userId) {
        Schedule schedule = scheduleRepository.findById(deleteScheduleReqDTO.getId())
                .orElseThrow(() -> NotFoundException.of("스케줄"));

        if (!schedule.getUser().getUserId().equals(userId)) {
            throw new UnauthorizedAccessException();
        }
        commentRepository.deleteBySchedule(schedule);
        scheduleRepository.delete(schedule);

    }
}
