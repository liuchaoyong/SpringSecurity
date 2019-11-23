package com.example.security.authorization;

import com.example.security.dao.PermissionDao;
import com.example.security.entity.Permission;
import com.example.security.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 权限资源管理器
 * 根据用户请求的地址，获取访问该地址需要的所需权限
 * @author Veiking
 */
@Component
public class VFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    @Autowired
    PermissionDao permissionDao;

    @Override
    @Transactional(readOnly = true)
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        //获取请求起源路径
        String requestUrl = ((FilterInvocation) object).getRequestUrl();
        //登录页面就不需要权限
        if ("/login".equals(requestUrl)) {
            return null;
        }
        //用来存储访问路径需要的角色或权限信息
        List<String> tempPermissionList = new ArrayList<String>();
        //获取角色列表
        Permission permissions = permissionDao.findFirstByUrl(requestUrl);
        if(!org.springframework.util.ObjectUtils.isEmpty(permissions) && !ObjectUtils.isEmpty(permissions.getRoleList()))
        for(Role role : permissions.getRoleList()) {
            tempPermissionList.add(role.getRole());
        }
        //径获取资源权限列表
        if(!org.springframework.util.ObjectUtils.isEmpty(permissions))
        tempPermissionList.add(permissions.getPermission());
        //如果没有权限控制的url，可以设置都可以访问，也可以设置默认不许访问
        if(tempPermissionList.isEmpty()) {
            return null;//都可以访问
            //tempPermissionList.add("DEFAULT_FORBIDDEN");//默认禁止
        }
        String[] permissionArray = tempPermissionList.toArray(new String[0]);
        return SecurityConfig.createList(permissionArray);
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

}
