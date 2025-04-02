package test.schedule.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import test.schedule.entity.Schedule;
import test.schedule.entity.User;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    @Query("SELECT s FROM Schedule s JOIN FETCH s.user u WHERE s.content LIKE %:keyword% OR u.name LIKE %:keyword% order by s.updatedAt desc")
    Page<Schedule> findByContentContaining(String keyword, Pageable pageable);

    List<Schedule> findByUser(User user);
}
