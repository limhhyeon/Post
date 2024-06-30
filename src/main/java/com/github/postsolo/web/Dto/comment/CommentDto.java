package com.github.postsolo.web.Dto.comment;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.postsolo.repository.comment.CommentEntity;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class CommentDto {
    private Integer postId;
    private String name;
    private String content;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy년 MM월 dd일 HH시 mm분")
    private LocalDateTime time;
    private Boolean status;

    public CommentDto(CommentEntity comment){
        this.postId=comment.getPost().getPostId();
        this.name=comment.getName();
        this.content= comment.getContent();
        this.time=comment.getCreateAt();
        this.status=comment.getStatus();
    }
}
