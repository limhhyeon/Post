package com.github.postsolo.repository.userRole;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleJpa extends JpaRepository<UserRole,Integer> {
}
