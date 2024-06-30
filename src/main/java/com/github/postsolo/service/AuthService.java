package com.github.postsolo.service;

import com.github.postsolo.config.security.JwtTokenProvider;
import com.github.postsolo.repository.roles.RoleJpa;
import com.github.postsolo.repository.roles.Roles;
import com.github.postsolo.repository.userRole.UserRole;
import com.github.postsolo.repository.userRole.UserRoleJpa;
import com.github.postsolo.repository.users.User;
import com.github.postsolo.repository.users.UserJpa;
import com.github.postsolo.service.exception.AlreadyExistsEmail;
import com.github.postsolo.web.Dto.ResponseDto;
import com.github.postsolo.web.Dto.auth.LoginDto;
import com.github.postsolo.web.Dto.auth.SignDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;



import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserJpa userJpa;
    private final RoleJpa roleJpa;
    private final UserRoleJpa userRoleJpa;

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;
    public ResponseDto signResult(SignDto signDto) {
        if (userJpa.existsByEmail(signDto.getEmail())){
            throw new AlreadyExistsEmail("이미 존재하는 이메일입니다.");
        }
        Roles roles = roleJpa.findByName("ROLE_USER");
            User user = User.builder()
                    .email(signDto.getEmail())
                    .password(passwordEncoder.encode(signDto.getPassword()))
                    .phoneNumber(signDto.getPhoneNumber())
                    .name(signDto.getName())
                    .createAt(LocalDateTime.now())
                    .build();
            userJpa.save(user);
        UserRole userRole = UserRole.builder()
                .user(user)
                .roles(roles)
                .build();
        userRoleJpa.save(userRole);
        return new ResponseDto(HttpStatus.OK.value(),"회원 가입 성공");
    }

    public ResponseDto loginResult(LoginDto loginDto, HttpServletResponse httpServletResponse) {
        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(),loginDto.getPassword())
                    );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            User user=userJpa.findByEmailFetchJoin(loginDto.getEmail())
                    .orElseThrow(()->new NotFoundException("해당 email: "+loginDto.getEmail()+"은 가입되어 있지 않습니다."));
            List<String> roles = user.getUserRole().stream().map(UserRole::getRoles).map(Roles::getName).collect(Collectors.toList());
            String jwtToken = jwtTokenProvider.createToken(user.getEmail(),roles);
            httpServletResponse.setHeader("Token", jwtToken);
            return new ResponseDto(HttpStatus.OK.value(),"로그인 성공");
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseDto(HttpStatus.BAD_REQUEST.value(), "로그인 실패");
        }


    }

    public ResponseDto logoutResult(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication(); //JwtAuthenticationFilter에서 setAuthentication했음
        if (auth!=null){
            String currentToken = jwtTokenProvider.resolveToken(httpServletRequest);
            jwtTokenProvider.addBlackList(currentToken);
            new SecurityContextLogoutHandler().logout(httpServletRequest, httpServletResponse, auth);
            return new ResponseDto((HttpStatus.OK.value()),"로그아웃 성공");
        }else
        return new ResponseDto(HttpStatus.BAD_REQUEST.value(), "로그아웃 실패");
    }
}
