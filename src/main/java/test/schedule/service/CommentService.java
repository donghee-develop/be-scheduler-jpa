package test.schedule.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import test.schedule.dto.comment.req.DeleteCommentReqDTO;
import test.schedule.dto.comment.req.PostCommentReqDTO;
import test.schedule.dto.comment.req.PutCommentReqDTO;
import test.schedule.dto.comment.res.GetCommentResDTO;
import test.schedule.dto.comment.res.PostCommentResDTO;
import test.schedule.dto.comment.res.PutCommentResDTO;
import test.schedule.entity.Comment;
import test.schedule.entity.Schedule;
import test.schedule.entity.User;
import test.schedule.exception.NotFoundException;
import test.schedule.exception.UnauthorizedAccessException;
import test.schedule.repository.CommentRepository;
import test.schedule.repository.ScheduleRepository;
import test.schedule.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {
    // 결합도 높음 facade 패턴 사용 가능
    private final CommentRepository commentRepository;
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    @Transactional
    public List<GetCommentResDTO> getCommentsByScheduleId(Long id) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> NotFoundException.of("스케줄"));
        List<Comment> comments = commentRepository.findBySchedule(schedule);

        return Optional.ofNullable(commentRepository.findBySchedule(schedule))
                .orElseGet(List::of)
                .stream()
                .map(GetCommentResDTO::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public PostCommentResDTO saveComment(PostCommentReqDTO postCommentReqDTO, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> NotFoundException.of("유저"));

        Schedule schedule = scheduleRepository.findById(postCommentReqDTO.getId())
                .orElseThrow(() -> NotFoundException.of("스케줄"));

        Comment comment = new Comment();
        comment.setContent(postCommentReqDTO.getContent());
        comment.setSchedule(schedule);
        comment.setUser(user);

        Comment savedComment = commentRepository.save(comment);

        return PostCommentResDTO.from(savedComment);
    }

    @Transactional
    public PutCommentResDTO updateByScheduleId(PutCommentReqDTO putCommentReqDTO, Long userId) {
        Comment comment = commentRepository.findById(putCommentReqDTO.getId())
                .orElseThrow(() -> NotFoundException.of("댓글"));

        if (!comment.getUser().getUserId().equals(userId)) {
            throw new UnauthorizedAccessException();
        }
        comment.setContent(putCommentReqDTO.getContent());
        Comment saveComment = commentRepository.save(comment);
        return PutCommentResDTO.from(saveComment);
    }
    @Transactional
    public void deleteByScheduleId(DeleteCommentReqDTO deleteCommentReqDTO, Long userId) {
        Comment comment = commentRepository.findById(deleteCommentReqDTO.getId())
                .orElseThrow(() -> NotFoundException.of("댓글"));

        if (!comment.getUser().getUserId().equals(userId)) {
            throw new UnauthorizedAccessException();
        }
        commentRepository.delete(comment);
    }
}
