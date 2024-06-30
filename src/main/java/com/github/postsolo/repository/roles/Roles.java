package com.github.postsolo.repository.roles;

import jakarta.persistence.*;
import lombok.*;

import javax.annotation.processing.Generated;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode(of="roleId")
@Entity
@Table(name = "role")
public class Roles {
    //role_id int AI PK
    //name varchar(100)
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Integer roleId;
    @Column(name = "name")
    private String name;
}
