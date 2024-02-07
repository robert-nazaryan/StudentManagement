package org.example.studentmanagement.config;

import lombok.RequiredArgsConstructor;
import org.example.studentmanagement.emun.UserType;
import org.example.studentmanagement.security.UserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final PasswordEncoder passwordEncoder;
    private final UserDetailService userDetailService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/").permitAll()
                .requestMatchers("register").permitAll()
                .requestMatchers("/login").permitAll()
                .requestMatchers("/student/register").permitAll()
                .requestMatchers("/teacher/register").permitAll()
                .requestMatchers("/lessons/add").hasAuthority(UserType.TEACHER.name())
                .requestMatchers("/lessons/update/**").hasAuthority(UserType.TEACHER.name())
                .requestMatchers("/lessons/delete/**").hasAuthority(UserType.TEACHER.name())
                .requestMatchers("/teachers/**").hasAuthority(UserType.TEACHER.name())
                .requestMatchers("/students/update/**").hasAuthority(UserType.TEACHER.name())
                .requestMatchers("/students/delete/**").hasAuthority(UserType.TEACHER.name())
                .anyRequest().hasAnyAuthority(UserType.TEACHER.name(), UserType.STUDENT.name())
                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/loginSuccess", true)
                .and()
                .logout()
                .logoutSuccessUrl("/");

        return httpSecurity.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        authenticationProvider.setUserDetailsService(userDetailService);
        return authenticationProvider;
    }

}
