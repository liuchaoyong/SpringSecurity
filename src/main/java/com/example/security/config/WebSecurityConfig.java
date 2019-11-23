package com.example.security.config;

import com.example.security.entity.User;
import org.apache.tomcat.util.http.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Security 主配置文件
 * @author Veiking
 */
@Configuration
@EnableWebSecurity //开启Spring Security的功能
@EnableGlobalMethodSecurity(prePostEnabled=true)//开启注解控制权限
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 定义不需要过滤的静态资源（等价于HttpSecurity的permitAll）
     */
    @Override
    public void configure(WebSecurity webSecurity) throws Exception {
        webSecurity.ignoring().antMatchers("/css/**");
    }




    /**
     * 安全策略配置
     */
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeRequests()
                // 对于网站部分资源需要指定鉴权
                //.antMatchers("/admin/**").hasRole("ADMIN")
                // 除上面外的所有请求全部需要鉴权认证
                .anyRequest().authenticated().and()
                // 定义当需要用户登录时候，转到的登录页面
                .formLogin()
                    .usernameParameter("uname")///登录表单form中用户名输入框input的name名，不修改的话默认是username
                    .passwordParameter("pword")//form中密码输入框input的name名，不修改的话默认是password
                    .defaultSuccessUrl("/index")//登录认证成功后默认转跳的路径
                    .and()
                // 定义登出操作
                .logout().logoutSuccessUrl("/login?logout").permitAll().and()
                .csrf().disable();
        // 禁用缓存
        httpSecurity.headers().cacheControl();

    }



    /**
     * 开启注解控制权限
     * 见Controller 中 @PreAuthorize("hasAuthority('XXX')")
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}