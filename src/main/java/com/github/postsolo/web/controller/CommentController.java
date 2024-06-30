package com.github.postsolo.web.controller;

import com.github.postsolo.repository.userDeatail.CustomUserDetails;
import com.github.postsolo.service.CommentService;
import com.github.postsolo.web.Dto.ResponseDto;
import com.github.postsolo.web.Dto.comment.CommentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    @PostMapping("/{postId}")
    public ResponseDto createComment(@AuthenticationPrincipal CustomUserDetails customUserDetails
            ,@PathVariable Integer postId
            ,@RequestBody CommentRequest commentRequest){
        return commentService.createCommentResult(customUserDetails,postId,commentRequest);

    }
    @PutMapping()
    public ResponseDto changeComment(@AuthenticationPrincipal CustomUserDetails customUserDetails
            ,@RequestParam("commentId") Integer commentId
            , @RequestBody CommentRequest commentRequest){
        return commentService.changeCommentResult(customUserDetails,commentId,commentRequest);

    }
    @DeleteMapping("/{postId}")
    public String deleteComment(@AuthenticationPrincipal CustomUserDetails customUserDetails
                                ,@PathVariable Integer postId
                                ,@RequestParam Integer commentId){
        return commentService.deleteCommentResult(customUserDetails,postId,commentId);

    }

}
