package com.github.postsolo.repository.like;

import com.github.postsolo.repository.Post.PostEntity;
import com.github.postsolo.repository.users.User;
import io.swagger.models.auth.In;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeJpa extends JpaRepository<LikeEntity, Integer> {
    Optional<LikeEntity> findByUserAndPost(User user, PostEntity post);
}
