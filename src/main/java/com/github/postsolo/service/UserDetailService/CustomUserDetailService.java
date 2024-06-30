package com.github.postsolo.service.UserDetailService;

import com.github.postsolo.repository.roles.Roles;
import com.github.postsolo.repository.userDeatail.CustomUserDetails;
import com.github.postsolo.repository.userRole.UserRole;
import com.github.postsolo.repository.users.User;
import com.github.postsolo.repository.users.UserJpa;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.stream.Collectors;

@Primary
@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private final UserJpa userJpa;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userJpa.findByEmailFetchJoin(email)
                .orElseThrow(()-> new NotFoundException("해당 User를 찾을 수 없습니다."));
        CustomUserDetails customUserDetails = CustomUserDetails.builder()
                .userId(user.getUserId())
                .password(user.getPassword())
                .email(user.getEmail())
                .authorities(user.getUserRole().stream().map(UserRole::getRoles).map(Roles::getName).collect(Collectors.toList()))
                .build();

        return customUserDetails;
    }
}
