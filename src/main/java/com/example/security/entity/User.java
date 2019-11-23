package com.example.security.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Table(name = "u_user")
@Entity
@Data
public class User implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    private String name;

    private String pwd;

    //多对多，关系维护端
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roleList;

}
