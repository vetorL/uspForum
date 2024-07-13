package com.example.uspForum.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/criar").hasAuthority("ROLE_USER")
                        .requestMatchers("/disciplina/postar/**").hasAuthority("ROLE_USER")
                        .requestMatchers("/disciplina/votar").hasAuthority("ROLE_USER")
                        .anyRequest().permitAll())
                .formLogin(form -> form
                        .loginPage("/login")
                        .permitAll()
                )
                .logout((logout) -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessUrl("/login?logout")
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                )
                .rememberMe(rememberMe -> rememberMe
                                // spring generates a random key on startup by default,
                                // the problem is that this invalidates all users' cookies
                                // everytime the app is restarted, making them log back inx
//                                .key("uniqueAndSecret")
                                .tokenValiditySeconds(86400*30) // token valid for 30 days
                );

        return http.build();
    }

    @Profile("dev")
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers(new AntPathRequestMatcher("/h2-console/**"));
    }

}
