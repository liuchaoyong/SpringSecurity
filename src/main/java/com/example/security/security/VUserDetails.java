package com.example.security.security;

import com.example.security.entity.Permission;
import com.example.security.entity.Role;
import com.example.security.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class VUserDetails extends User implements UserDetails {

    public VUserDetails(User user){
        super.setId(user.getId());
        super.setName(user.getName());
        super.setPwd(user.getPwd());
        super.setRoleList(user.getRoleList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        StringBuilder authoritiesBuilder = new StringBuilder("");
        List<Role> tempRoleList = super.getRoleList();
        if (null != tempRoleList) {
            for (Role role : tempRoleList) {
                authoritiesBuilder.append(",").append(role.getRole());
            }
        }

        if(super.getRoleList() != null && super.getRoleList().size() > 0){
            for (Role role : super.getRoleList()){
                for (Permission p : role.getPermissionList()){
                    authoritiesBuilder.append(",").append(p.getPermission());
                }
            }
        }

        String authoritiesStr = "";
        if(authoritiesBuilder.length()>0) {
            authoritiesStr = authoritiesBuilder.deleteCharAt(0).toString();
        }
        return AuthorityUtils.commaSeparatedStringToAuthorityList(authoritiesStr);
    }

    @Override
    public String getPassword() {
        return super.getPwd();
    }

    @Override
    public String getUsername() {
        return super.getName();
    }

    /**
     * 判断账号是否已经过期，默认没有过期
     */
    @Override
    public boolean isAccountNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    /**
     * 判断账号是否被锁定，默认没有锁定
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 判断信用凭证是否过期，默认没有过期
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 判断账号是否可用，默认可用
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
