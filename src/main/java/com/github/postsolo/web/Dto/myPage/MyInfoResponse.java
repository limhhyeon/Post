package com.github.postsolo.web.Dto.myPage;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MyInfoResponse {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String email;
    private String name;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String password;
    private String phoneNum;


}
