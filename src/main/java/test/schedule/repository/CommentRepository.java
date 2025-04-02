package test.schedule.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import test.schedule.entity.Comment;
import test.schedule.entity.Schedule;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    void deleteByScheduleIn(List<Schedule> schedules);

    void deleteBySchedule(Schedule schedule);

    List<Comment> findBySchedule(Schedule schedule);
}
