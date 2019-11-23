package com.example.security.dao;

import com.example.security.entity.Permission;
import com.example.security.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface PermissionDao extends JpaRepository<Permission,Long> {
    Permission findFirstByUrl(String url);
}
