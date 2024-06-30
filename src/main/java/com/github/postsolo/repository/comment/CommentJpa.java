package com.github.postsolo.repository.comment;

import com.github.postsolo.repository.Post.PostEntity;
import com.github.postsolo.repository.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentJpa extends JpaRepository<CommentEntity,Integer> {
    List<CommentEntity> findAllByPost(PostEntity post);
    List<CommentEntity> findAllByUser(User user);
}
