package com.sh.spr_login.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityJavaConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().disable()  // Todo 해제 - h2 db이용 위해
                .csrf().disable()
                .formLogin().disable()  // 기본 로그인 화면 제거
                .headers().frameOptions().disable()  // Todo H2db iframe
                ;
//        http.authorizeRequests()
//                .mvcMatchers("/", "/register/**").permitAll()
//                .mvcMatchers("/admin").hasRole("ADMIN")
//                .anyRequest().authenticated();
//        http.formLogin();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
