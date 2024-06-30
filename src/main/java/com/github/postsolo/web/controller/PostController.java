package com.github.postsolo.web.controller;


import com.github.postsolo.repository.userDeatail.CustomUserDetails;
import com.github.postsolo.service.PostService;
import com.github.postsolo.web.Dto.ResponseDto;
import com.github.postsolo.web.Dto.post.PostCreateDto;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;



@RequiredArgsConstructor
@RestController
@RequestMapping("/post")
public class PostController {
    private final PostService postService;

    @PostMapping()
    public ResponseDto createPost(@AuthenticationPrincipal CustomUserDetails customUserDetails
            , @RequestBody PostCreateDto postDto){
        return postService.createPostResult(customUserDetails,postDto);
    }
    @GetMapping()
    public ResponseDto getAllPost(){
        return postService.getAllPostResult();

    }
    @GetMapping("/{postId}")
    public ResponseDto getPostByPostId(@AuthenticationPrincipal CustomUserDetails customUserDetails,@PathVariable Integer postId){
        return postService.getPostByPostIdResult(customUserDetails,postId);
    }
    @GetMapping("/{keyword}")
    public ResponseDto getPostByKeyword(@PathVariable String keyword){
        return postService.getPostAllByKeywordResult(keyword);
    }
    @PutMapping("/{postId}")
    public ResponseDto updatePost(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                  @PathVariable Integer postId,
                                  @RequestBody PostCreateDto postCreateDto){
        return postService.updatePostResult(customUserDetails,postId,postCreateDto);
    }
    @DeleteMapping("/{postId}")
    public ResponseDto deletePost(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                  @PathVariable Integer postId){
        return postService.deletePostResult(customUserDetails,postId);
    }
    @PostMapping("/like/{postId}")
    public String likePost(@AuthenticationPrincipal CustomUserDetails customUserDetails
            ,@PathVariable Integer postId){
        return postService.likePostResult(customUserDetails,postId);
    }

}
