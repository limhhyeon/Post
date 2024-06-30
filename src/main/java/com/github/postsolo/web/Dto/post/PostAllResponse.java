package com.github.postsolo.web.Dto.post;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.postsolo.repository.Post.PostEntity;
import lombok.*;

import java.time.LocalDateTime;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class PostAllResponse {
    private String title;
    private String name;
    @JsonFormat(shape=JsonFormat.Shape.STRING,pattern = "yyyy년 MM월 dd일 HH시 mm분")
    private LocalDateTime creatAt;
    private Integer likeCnt;

    public PostAllResponse(PostEntity postEntity){
        this.title = postEntity.getTitle();
        this.name=postEntity.getName();
        this.creatAt =postEntity.getCreateAt();
        this.likeCnt=postEntity.getLikenCnt();
    }

}
