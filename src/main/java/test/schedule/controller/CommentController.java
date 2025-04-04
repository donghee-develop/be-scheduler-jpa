package test.schedule.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import test.schedule.dto.comment.req.DeleteCommentReqDTO;
import test.schedule.dto.comment.req.PostCommentReqDTO;
import test.schedule.dto.comment.req.PutCommentReqDTO;
import test.schedule.dto.comment.res.GetCommentResDTO;
import test.schedule.dto.user.security.UserDTO;
import test.schedule.service.CommentService;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @GetMapping
    public List<GetCommentResDTO> getComments(
            @RequestParam Long id
    ) {
        return commentService.getCommentsByScheduleId(id);
    }
    @PostMapping
    public ResponseEntity<GetCommentResDTO> postComment(
            HttpServletRequest request,
            @Valid @RequestBody PostCommentReqDTO postCommentReqDTO
    ){
        HttpSession session = request.getSession(false);
        UserDTO loginUser = (UserDTO) session.getAttribute("user");

        GetCommentResDTO savedComment = commentService.saveComment(postCommentReqDTO, loginUser.getUserId());
        return new ResponseEntity<>(savedComment, HttpStatus.OK);
    }
    @PutMapping
    public ResponseEntity<Void> updateComment(
            HttpServletRequest request,
            @Valid @RequestBody PutCommentReqDTO putCommentReqDTO
    ){
        HttpSession session = request.getSession(false);
        UserDTO loginUser = (UserDTO) session.getAttribute("user");
        commentService.updateByScheduleId(putCommentReqDTO,loginUser.getUserId());
        return ResponseEntity.ok().build();
    }
    @DeleteMapping
    public ResponseEntity<Void> deleteComment(
            HttpServletRequest request,
            @Valid @RequestBody DeleteCommentReqDTO deleteCommentReqDTO
    ){
        HttpSession session = request.getSession(false);
        UserDTO loginUser = (UserDTO) session.getAttribute("user");
        commentService.deleteByScheduleId(deleteCommentReqDTO,loginUser.getUserId());
        return ResponseEntity.ok().build();
    }
}
