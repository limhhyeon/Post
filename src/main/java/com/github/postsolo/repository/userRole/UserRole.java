package com.github.postsolo.repository.userRole;

import com.github.postsolo.repository.roles.Roles;
import com.github.postsolo.repository.users.User;
import jakarta.persistence.*;
import lombok.*;

import javax.management.relation.Role;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode(of = "userRoleId")
@Entity
@Table(name = "user_role")
@Builder
public class UserRole {
//user_role_id int AI PK
//user_id int
//role_id int
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_role_id")
    private Integer userRoleId;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id",nullable = false)
    private User user;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id",nullable = false)
    private Roles roles;


}
