package com.github.postsolo.repository.comment;

import com.github.postsolo.repository.Post.PostEntity;
import com.github.postsolo.repository.users.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@EqualsAndHashCode(of = "commentId")
@Entity
@Table(name = "comment")

public class CommentEntity {
    //comment_id int AI PK
    //user_id int
    //post_id int
    //name varchar(100)
    //content varchar(500)
    //create_at datetime
    @Id@GeneratedValue
    @Column(name = "comment_id")
    private Integer commentId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private PostEntity post;
    @Column(name = "name",length = 100,nullable = false)
    private String name;
    @Column(name = "content",length = 500,nullable = false)
    private String content;
    @Column(name = "create_at",nullable = false)
    private LocalDateTime createAt;
    @Column(name = "status",nullable = false)
    private Boolean status;

}
