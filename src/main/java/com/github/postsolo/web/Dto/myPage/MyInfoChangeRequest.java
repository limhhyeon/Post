package com.github.postsolo.web.Dto.myPage;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MyInfoChangeRequest {
    private String name;
    private String phoneNumber;
    private String password;
}
