package com.github.postsolo.web.Dto.auth;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignDto {
    private String email;
    private String password;
    private String name;
    private String phoneNumber;

}
