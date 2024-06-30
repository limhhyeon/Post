package com.github.postsolo.service;

import com.github.postsolo.repository.Post.PostEntity;
import com.github.postsolo.repository.Post.PostJpa;
import com.github.postsolo.repository.userDeatail.CustomUserDetails;
import com.github.postsolo.repository.users.User;
import com.github.postsolo.repository.users.UserJpa;
import com.github.postsolo.service.exception.NotFoundException;
import com.github.postsolo.web.Dto.myPage.MyInfoChangeRequest;
import com.github.postsolo.web.Dto.myPage.MyInfoResponse;
import com.github.postsolo.web.Dto.post.PostAllResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class MyPageService {
    private final PostJpa postJpa;
    private final UserJpa userJpa;
    private final PasswordEncoder passwordEncoder;
    public List<PostAllResponse> myWritePostResult(CustomUserDetails customUserDetails) {
        Integer userId = customUserDetails.getUserId();
        User user = userJpa.findById(userId)
                .orElseThrow(()-> new NotFoundException("해당 유저를 찾을 수 없습니다."));
        List<PostEntity> postEntitiesByUser = postJpa.findAllByUser(user);
        List<PostAllResponse> postEntityToPostDtoList = postEntitiesByUser.stream().map(PostAllResponse::new).toList();
        return postEntityToPostDtoList;
    }

    public MyInfoResponse myInfoResult(CustomUserDetails customUserDetails) {
        Integer userId = customUserDetails.getUserId();
        User user = userJpa.findById(userId)
                .orElseThrow(()-> new NotFoundException("해당 유저를 찾을 수 없습니다."));
        return MyInfoResponse.builder()
                .email(user.getEmail())
                .name(user.getName())
                .phoneNum(user.getPhoneNumber())
                .build();

    }

    public MyInfoResponse myInfoChangeResult(CustomUserDetails customUserDetails, MyInfoChangeRequest myInfoChangeRequest) {
        Integer userId = customUserDetails.getUserId();
        User user = userJpa.findById(userId)
                .orElseThrow(()-> new NotFoundException("해당 유저를 찾을 수 없습니다."));
        user.setName(myInfoChangeRequest.getName());
        user.setPassword(passwordEncoder.encode(myInfoChangeRequest.getPassword()));
        user.setPhoneNumber(myInfoChangeRequest.getPhoneNumber());
        return MyInfoResponse.builder()
                .name(user.getName())
                .password("**************")
                .phoneNum(user.getPhoneNumber())
                .build();


    }
}
