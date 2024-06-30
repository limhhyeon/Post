package com.github.postsolo.repository.users;

import com.github.postsolo.repository.userRole.UserRole;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode(of="userId")
@Entity
@Table(name = "user")
@Builder
public class User {
    //user_id int AI PK
    //email varchar(255)
    //password varchar(255)
    //name varchar(255)
    //phone_number varchar(255)
    //create_at datetime
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "email",nullable = false)
    private String email;
    @Column(name = "password",nullable = false)
    private String password;
    @Column(name = "name",nullable = false)
    private String name;
    @Column(name = "phone_number",nullable = false)
    private String phoneNumber;
    @Column(name = "create_at")
    private LocalDateTime createAt;
    @OneToMany(mappedBy= "user", orphanRemoval = true)
    private List<UserRole> userRole;
}
