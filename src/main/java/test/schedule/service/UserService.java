package test.schedule.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import test.schedule.config.PasswordEncoder;
import test.schedule.dto.user.req.PostUserReqDTO;
import test.schedule.dto.user.req.PutUserReqDTO;
import test.schedule.dto.user.security.UserDTO;
import test.schedule.entity.Schedule;
import test.schedule.entity.User;
import test.schedule.exception.DuplicateException;
import test.schedule.exception.NotFoundException;
import test.schedule.repository.CommentRepository;
import test.schedule.repository.ScheduleRepository;
import test.schedule.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final ScheduleRepository scheduleRepository;
    private final CommentRepository commentRepository;


    public UserDTO login(String email, String password) {
        passwordEncoder.encode(password);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> NotFoundException.of("이메일"));
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        return new UserDTO(user);
    }

    @Transactional
    public void saveUser(PostUserReqDTO postUserReqDTO) {
        if(userRepository.findByEmail(postUserReqDTO.getEmail()).isPresent()) {
            throw DuplicateException.of("이메일");
        }
        User user = new User();
        user.setName(postUserReqDTO.getName());
        user.setEmail(postUserReqDTO.getEmail());
        user.setPassword(passwordEncoder.encode(postUserReqDTO.getPassword()));

        userRepository.save(user);
    }

    // 동시성 문제 발생할 수 있음, 처리 안함
    // 정말 미세한 시간에 발생할 수 있는데 이 부분을 처리해야할까
    @Transactional
    public void updateUser(PutUserReqDTO putUserReqDTO,Long userId) {
        // 세션으로 받아서 프론트 조작 예방
        User user = userRepository.findById(userId)
                .orElseThrow(() -> NotFoundException.of("유저"));
        userRepository.findByEmail(putUserReqDTO.getEmail()).ifPresent(existingUser -> {
            if (!existingUser.getUserId().equals(userId)) {
                throw DuplicateException.of("이메일");
            }
        });

        user.setEmail(putUserReqDTO.getEmail());
        user.setName(putUserReqDTO.getName());
        user.setPassword(passwordEncoder.encode(putUserReqDTO.getPassword()));

        userRepository.save(user);
    }

    @Transactional
    public void deleteUser(Long userId) {
        // 세션으로 유저 조회함 DTO로 받는다면 프론트에서 값 바꿔서 보낼 수 있음
        // 예외 발생하지 않을 거임 세션값에서 가져옴
        User user = userRepository.findById(userId)
                .orElseThrow(() -> NotFoundException.of("유저"));
        // 유저랑 관계있는 스케줄 댓글 삭제 시킴 그리고 유저 삭제함
        List<Schedule> schedules = scheduleRepository.findByUser(user);
        commentRepository.deleteByScheduleIn(schedules);
        scheduleRepository.deleteAll(schedules);
        userRepository.delete(user);
    }
}
