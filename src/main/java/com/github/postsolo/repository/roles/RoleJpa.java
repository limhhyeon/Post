package com.github.postsolo.repository.roles;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleJpa extends JpaRepository<Roles,Integer> {
    Roles findByName(String name);
}
