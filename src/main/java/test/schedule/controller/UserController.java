package test.schedule.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import test.schedule.dto.user.req.LoginReqDTO;
import test.schedule.dto.user.req.PostUserReqDTO;
import test.schedule.dto.user.req.PutUserReqDTO;
import test.schedule.dto.user.security.UserDTO;
import test.schedule.service.UserService;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @PostMapping("/login")
    public ResponseEntity<Void> loginUser(
            HttpServletRequest request,
            @Valid @RequestBody LoginReqDTO loginReqDTO
    ){
        UserDTO user = userService.login(loginReqDTO.getEmail(), loginReqDTO.getPassword());
        HttpSession session = request.getSession();
        session.setAttribute("user", user);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    @PostMapping("/logout")
    public ResponseEntity<Void> logoutUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        session.invalidate();

        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<Void> postUser(
            @Valid @RequestBody PostUserReqDTO postUserReqDTO
    ){
        userService.saveUser(postUserReqDTO);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    @PutMapping
    public ResponseEntity<Void> putUser(
            HttpServletRequest request,
            @Valid @RequestBody PutUserReqDTO putUserReqDTO
    ){
        HttpSession session = request.getSession(false);
        UserDTO loginUser = (UserDTO) session.getAttribute("user");

        userService.updateUser(putUserReqDTO,loginUser.getUserId());
        return ResponseEntity.ok().build();
    }
    @DeleteMapping
    public ResponseEntity<Void> deleteUser(
            HttpServletRequest request
    ){
        // 아이디만 받기 때문에 세션에 있는 아이디를 받아서 삭제
        HttpSession session = request.getSession(false);
        UserDTO loginUser = (UserDTO) session.getAttribute("user");
        userService.deleteUser(loginUser.getUserId());
        session.invalidate();

        return ResponseEntity.ok().build();
    }
}
