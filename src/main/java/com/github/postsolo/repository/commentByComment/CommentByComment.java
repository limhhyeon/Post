package com.github.postsolo.repository.commentByComment;

import com.github.postsolo.repository.comment.CommentEntity;
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
@EqualsAndHashCode(of = "commentByCommentId")
@Entity
@Table(name = "commentByComment")
public class CommentByComment {
    //comment_by_comment_id int AI PK
    //user_id int
    //comment_id int
    //name varchar(100)
    //content varchar(500)
    //create_at datetime
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_by_comment_id")
    private Integer commentByCommentId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false)
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id",nullable = false)
    private CommentEntity comment;
    @Column(name = "name",length = 100,nullable = false)
    private String name;
    @Column(name = "content",length = 500,nullable = false)
    private String content;
    @Column(name = "create_at",nullable = false)
    private LocalDateTime createAt;
}
