package com.github.postsolo.web.Dto.comment;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class CommentRequest {
    private String content;
    private Boolean status;
}
