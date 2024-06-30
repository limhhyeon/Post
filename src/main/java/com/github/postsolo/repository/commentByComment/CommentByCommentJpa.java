package com.github.postsolo.repository.commentByComment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentByCommentJpa extends JpaRepository<CommentByComment,Integer> {
}
