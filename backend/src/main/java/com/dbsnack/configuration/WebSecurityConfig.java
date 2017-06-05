package com.dbsnack.configuration;

import com.dbsnack.configuration.jwt.JWTConfigurer;
import com.dbsnack.configuration.jwt.TokenProvider;
import com.dbsnack.services.user.StandardUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private StandardUserService userService;

    @Autowired
    private TokenProvider tokenProvider;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                    .antMatchers("/", "/login", "/register", "/trainings/**", "/connect/**", "/account/**").permitAll()
                    .antMatchers("/api/authenticate","/api/register", "/api/trainings", "/api/facebook/**", "/api/account/**").permitAll()
                    .antMatchers("/editor/**").hasAnyRole("ADMIN", "EDITOR")
                    .antMatchers("/admin/**").hasRole("ADMIN")
                    .anyRequest().authenticated()
                .anyRequest().permitAll()
                .and()
                .apply(securityConfigurerAdapter());
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder());
    }

    private JWTConfigurer securityConfigurerAdapter() {
        return new JWTConfigurer(tokenProvider);
    }
}
