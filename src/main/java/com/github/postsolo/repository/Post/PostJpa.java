package com.github.postsolo.repository.Post;

import com.github.postsolo.repository.users.User;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;
@Repository
public interface PostJpa extends JpaRepository<PostEntity,Integer> {




    @Query("SELECT p FROM PostEntity p WHERE LOWER(p.title) LIKE %:keyword% OR LOWER(p.content) LIKE %:keyword% OR LOWER(p.name) LIKE %:keyword%")
    List<PostEntity> findByKeyword(String keyword);

    List<PostEntity> findAllByUser(User user);

}
