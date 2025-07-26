package com.fw.fo.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * 스프링 시큐리티 설정
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class FoWebSecurityConfig {

  /*  private final BoLoginAuthFailureHandler boLoginAuthFailureHandler;
    private final BoLoginAuthSuccessHandler boLoginAuthSuccessHandler;*/

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .anyRequest()
                //.permitAll()
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/bo/login")
                .usernameParameter("adminId")
                .passwordParameter("adminPassword")
                .loginProcessingUrl("/bo/login/process")
/*                .successHandler(boLoginAuthSuccessHandler)
                .failureHandler(boLoginAuthFailureHandler)*/
                .permitAll();

        http.headers().frameOptions().sameOrigin();

        http.logout().logoutUrl("/bo/logout").permitAll()
                .logoutSuccessUrl("/bo/login")
                .deleteCookies("BO-JSESSIONID")
                .invalidateHttpSession(true);

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/static/**","/error/**","/contact/**","/email/**","/change/**","/**");
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

   /* @Bean
    public AuthenticationProvider authenticationProvider() {
        return new BoAuthenticationProvider();
    }*/

}