package com.github.postsolo.repository.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserJpa extends JpaRepository<User,Integer> {
    @Query("SELECT u FROM User u JOIN FETCH u.userRole ur JOIN FETCH ur.roles WHERE u.email = :email")
    Optional<User> findByEmailFetchJoin(String email);

    Boolean existsByEmail(String email);
}
