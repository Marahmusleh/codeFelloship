package com.application2.codeFellowship.security;

import com.application2.codeFellowship.Repository.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {//extends abstract class

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Bean // applied on a method to specify that it returns a bean to be managed by Spring context.
   public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.cors().disable().csrf().disable()
                .authorizeRequests().antMatchers( "/login", "/signup","/").permitAll()
                .anyRequest().authenticated().and().formLogin().
                loginPage("/login").loginProcessingUrl("/perform_login").
                defaultSuccessUrl("/", true).
                failureUrl("/error").and().logout().logoutUrl("/perform_logout").
                deleteCookies("JSESSIONID");


    }

}
