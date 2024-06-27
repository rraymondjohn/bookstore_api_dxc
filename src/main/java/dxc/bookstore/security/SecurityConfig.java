package dxc.bookstore.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Collections;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager userDetailsService = new InMemoryUserDetailsManager();

        userDetailsService.createUser(User.withUsername("admin").password("{noop}pass").roles("ADMIN").build());
        userDetailsService.createUser(User.withUsername("user").password("{noop}pass").roles("USER").build());

        return userDetailsService;
    }

    @Bean
    public CorsConfigurationSource corsConfiguration() {
        return request -> {
            org.springframework.web.cors.CorsConfiguration config =
                    new org.springframework.web.cors.CorsConfiguration();
            config.setAllowedHeaders(Collections.singletonList("*"));
            config.setAllowedMethods(Collections.singletonList("*"));
            config.setAllowedOriginPatterns(Collections.singletonList("*"));
            config.setAllowCredentials(true);
            return config;
        };
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors(corsConfigurer -> corsConfigurer.configurationSource(corsConfiguration()))
                .csrf(AbstractHttpConfigurer::disable);
        http.securityMatcher("/bookstore/api/book/delete/**")
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().hasRole("ADMIN"))
                .httpBasic(withDefaults());

        return http.build();
    }
}
