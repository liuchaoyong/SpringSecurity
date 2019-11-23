package com.example.security.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Table(name = "u_role")
@Entity
@Data
public class Role {
    @Id
    @GeneratedValue
    private long id;

    private String role;

    private String describes;

    @JsonIgnore
    @ManyToMany(mappedBy = "roleList")
    //mappedBy的参数是User类里面的被ManyToMany注释的属性名
    private List<User> userList;

    //多对多，关系维护端
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "role_permissions", joinColumns = @JoinColumn(name = "role_id"), inverseJoinColumns = @JoinColumn(name = "permission_id"))
    private List<Permission> permissionList;
}
