package com.dusan.webshop.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class UserRole {

    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_role_id_sequence")
    @SequenceGenerator(name = "user_role_id_sequence", sequenceName = "user_role_id_sequence", allocationSize = 1)
    private Long id;

    private String name;
}
