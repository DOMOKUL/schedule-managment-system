package com.company.schedule.management.system.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().disable();
        http.csrf().disable();
        http.headers().frameOptions().disable();

        http.authorizeRequests().antMatchers(HttpMethod.POST).hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.PUT).hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.DELETE).hasAnyRole("ADMIN")

                .antMatchers(HttpMethod.POST, "/api/v1/students").hasRole("TEACHER")
                .antMatchers(HttpMethod.POST, "/api/v1/groups").hasRole("TEACHER")
                .antMatchers(HttpMethod.POST, "/api/v1/subjects").hasRole("TEACHER")
                .antMatchers(HttpMethod.POST, "/api/v1/lessons").hasRole("TEACHER")
                .antMatchers(HttpMethod.POST, "/api/v1/lectures").hasRole("TEACHER")

                .antMatchers(HttpMethod.GET).hasAnyRole("ADMIN", "TEACHER", "STUDENT")

                .antMatchers(HttpMethod.GET, "/api/v1/users").hasAnyRole("ADMIN", "TEACHER");
        super.configure(http);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
