package com.github.postsolo.web.controller;

import com.github.postsolo.service.AuthService;
import com.github.postsolo.web.Dto.ResponseDto;
import com.github.postsolo.web.Dto.auth.LoginDto;
import com.github.postsolo.web.Dto.auth.SignDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class SignController {
    private final AuthService authService;

    @PostMapping("/sign-up")
    public ResponseDto signUp(@RequestBody SignDto signDto){
        return authService.signResult(signDto);
    }
    @PostMapping("/login")
    public ResponseDto login(@RequestBody LoginDto loginDto, HttpServletResponse httpServletResponse){
        return authService.loginResult(loginDto,httpServletResponse);

    }
    @PostMapping("/logout")
    public ResponseDto logout(HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest){
        return authService.logoutResult(httpServletRequest,httpServletResponse);
    }
}
