package test.schedule.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import test.schedule.dto.schedule.req.DeleteScheduleReqDTO;
import test.schedule.dto.schedule.res.GetScheduleResDTO;
import test.schedule.dto.schedule.req.PostScheduleReqDTO;
import test.schedule.dto.schedule.req.PutScheduleReqDTO;
import test.schedule.dto.user.security.UserDTO;
import test.schedule.entity.Schedule;
import test.schedule.service.ScheduleService;

@RestController
@RequestMapping("/api/schedules")
@RequiredArgsConstructor
public class ScheduleController {
    private final ScheduleService scheduleService;
    // 불변 컨트롤러 객체를 생성할 일 테스트코드 시 생김
    // 생성자 자연스러움 필드 리플랙션 써야함 지양함
    @GetMapping
    public ResponseEntity<Page<GetScheduleResDTO>> getSchedule(
            @RequestParam(required = false, defaultValue = "") String keyword,
            @PageableDefault(size = 10, sort = "updatedAt", direction = Sort.Direction.DESC) Pageable pageable

    ){
        Page<GetScheduleResDTO> getScheduleResDTOS = scheduleService.searchSchedules(keyword, pageable);
        return ResponseEntity.ok(getScheduleResDTOS);
    }
    @PostMapping
    public ResponseEntity<Void> postSchedule(
            @Valid @RequestBody PostScheduleReqDTO postScheduleReqDTO,
            HttpServletRequest request
    ){
        HttpSession session = request.getSession(false);
        UserDTO loginUser = (UserDTO) session.getAttribute("user");
        scheduleService.saveSchedule(postScheduleReqDTO,loginUser.getUserId());
        return ResponseEntity.ok().build();
    }
    @GetMapping("/{id}")
    public ResponseEntity<GetScheduleResDTO> getSchedule(
            @PathVariable Long id
    ){
        Schedule schedule = scheduleService.getScheduleById(id);
        return ResponseEntity.ok(GetScheduleResDTO.from(schedule,0));
    }
    @PutMapping
    public ResponseEntity<Void> putSchedule(
            @Valid @RequestBody PutScheduleReqDTO putScheduleReqDTO,
            HttpServletRequest request
    ){
        HttpSession session = request.getSession(false);
        UserDTO loginUser = (UserDTO) session.getAttribute("user");
        scheduleService.updateSchedule(putScheduleReqDTO, loginUser.getUserId());

        return ResponseEntity.ok().build();
    }
    @DeleteMapping
    public ResponseEntity<Void> deleteSchedule(
            @Valid @RequestBody DeleteScheduleReqDTO deleteScheduleReqDTO,
            HttpServletRequest request
    ){
        // SAVE UPDATE 메소드 이름으로 따라가기
        HttpSession session = request.getSession(false);
        UserDTO loginUser = (UserDTO) session.getAttribute("user");
        scheduleService.deleteSchedule(deleteScheduleReqDTO,loginUser.getUserId());
        return ResponseEntity.ok().build();
    }

}
