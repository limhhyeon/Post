package com.github.postsolo.web.controller;

import com.github.postsolo.repository.userDeatail.CustomUserDetails;
import com.github.postsolo.service.MyPageService;
import com.github.postsolo.web.Dto.myPage.MyInfoChangeRequest;
import com.github.postsolo.web.Dto.myPage.MyInfoResponse;
import com.github.postsolo.web.Dto.post.PostAllResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/myPage")
@RequiredArgsConstructor
public class MyPageController {
    private final MyPageService myPageService;
    @GetMapping("/mypost")
    public List<PostAllResponse> myWritePost(@AuthenticationPrincipal CustomUserDetails customUserDetails){
        return myPageService.myWritePostResult(customUserDetails);
    }
    @GetMapping("/my-info")
    public MyInfoResponse getMyInfo(@AuthenticationPrincipal CustomUserDetails customUserDetails){
        return myPageService.myInfoResult(customUserDetails);
    }
    @PutMapping("/my-info-change")
    public MyInfoResponse changeMyInfo(@AuthenticationPrincipal CustomUserDetails customUserDetails
            , @RequestBody MyInfoChangeRequest myInfoChangeRequest){
        return myPageService.myInfoChangeResult(customUserDetails,myInfoChangeRequest);
    }

}
