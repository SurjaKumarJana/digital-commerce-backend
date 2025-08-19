package org.surja.digital_commerce_backend.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.surja.digital_commerce_backend.entity.Role;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

            http
                .csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable())
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/api/admin/**").hasAnyAuthority(Role.ADMIN.name())
                            .requestMatchers("/api/seller/**").hasAnyAuthority(Role.SELLER.name())
                            .requestMatchers("/api/customer/**").hasAnyAuthority(Role.CUSTOMER.name())
                            .requestMatchers("/public/**", "/content/**").permitAll()
                            .anyRequest().authenticated();
                })
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults());

            return http.build();
    }


}
