package com.example.security.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.List;
@Table(name = "u_permission")
@Data
@Entity
public class Permission {
    @Id
    @GeneratedValue
    private long id;

    private String permission;

    private String url;

    private String describes;

    @JsonIgnore
    @ManyToMany(mappedBy = "permissionList",fetch = FetchType.EAGER)
    //mappedBy的参数是User类里面的被ManyToMany注释的属性名
    private List<Role> roleList;
}
