package com.github.postsolo.repository.Post;

import com.github.postsolo.repository.users.User;
import io.swagger.models.auth.In;
import jakarta.persistence.*;
import lombok.*;
import net.sf.jsqlparser.statement.select.Fetch;

import java.time.LocalDateTime;
import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "postId")
@Builder
@Entity
@Table(name = "post")
public class PostEntity {
    //post_id int AI PK
    //user_id int
    //title varchar(255)
    //name varchar(100)
    //content varchar(500)
    //create_at datetime
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Integer postId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @Column(name = "title",length = 255, nullable = false)
    private String title;
    @Column(name = "name",length = 100,nullable = false)
    private String name;
    @Column(name = "content",length = 500,nullable = false)
    private String content;
    @Column(name = "create_at",nullable = false)
    private LocalDateTime createAt;
    @Column(name = "like_cnt",nullable = false)
    private Integer likenCnt;

    public Optional<User> getUser() {
        return Optional.ofNullable(user);
    }
}
