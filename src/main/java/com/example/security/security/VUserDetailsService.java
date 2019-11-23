package com.example.security.security;

import com.example.security.dao.UserDao;
import com.example.security.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class VUserDetailsService implements UserDetailsService {

    @Autowired
    UserDao userDao;
    /**
     * 根据用户输入的用户名返回数据源中用户信息的封装，返回一个UserDetails
     */
    @Override
    public UserDetails loadUserByUsername(String username)  {
        User user = userDao.findByName(username);
        return new VUserDetails(user);
    }

}