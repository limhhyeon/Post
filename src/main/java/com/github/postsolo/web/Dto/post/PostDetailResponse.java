package com.github.postsolo.web.Dto.post;

import com.github.postsolo.web.Dto.comment.CommentDto;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class PostDetailResponse {
    private String title;
    private String content;
    private String name;
    private Integer likeCnt;
    private LocalDateTime createAt;
    private List<CommentDto> comments;
}
