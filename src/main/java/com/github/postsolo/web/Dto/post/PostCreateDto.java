package com.github.postsolo.web.Dto.post;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class PostCreateDto {
    private String title;
    private String content;

}
