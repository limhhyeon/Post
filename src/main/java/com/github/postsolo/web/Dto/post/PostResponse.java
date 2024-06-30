package com.github.postsolo.web.Dto.post;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.postsolo.repository.Post.PostEntity;
import jakarta.persistence.GeneratedValue;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class PostResponse {
    private String title;
    private String content;
    private String name;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy년 MM월 dd일 HH시 mm분")
    private LocalDateTime createAt;
    private Integer likeCnt;

    public PostResponse(PostEntity postEntity){
    this.title=postEntity.getTitle();
    this.content=postEntity.getContent();
    this.name=postEntity.getName();
    this.createAt=postEntity.getCreateAt();
    this.likeCnt=postEntity.getLikenCnt();
    }
}
