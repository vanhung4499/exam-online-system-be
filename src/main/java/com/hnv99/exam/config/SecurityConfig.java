package com.hnv99.exam.config;


import com.hnv99.exam.common.result.Result;
import com.hnv99.exam.filter.VerifyTokenFilter;
import com.hnv99.exam.util.ResponseUtil;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Resource
    private ResponseUtil responseUtil;
    @Resource
    private VerifyTokenFilter verifyTokenFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(request -> {
            // Allow authentication
            request.requestMatchers("/api/auths/**").permitAll();
            request.requestMatchers("/api/auths/logout").authenticated();
            // All requests need to be authenticated
            request.anyRequest().authenticated();
        });

        // Disable form login, use custom login and logout
        http.formLogin(AbstractHttpConfigurer::disable);

        // Configure access denied handler
        http.exceptionHandling(exceptionHandling -> exceptionHandling
                .accessDeniedHandler((request, response, accessDeniedException) ->
                        responseUtil.response(response, Result.failed("You do not have access to this resource"))));
        // Configure request interception handler to verify token
        http.addFilterBefore(verifyTokenFilter, UsernamePasswordAuthenticationFilter.class);
        // Allow cross-origin requests
        http.csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }

    /**
     * Configuration to bypass the filter chain
     */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .requestMatchers(
                        // "/api/auths/**",
                        "/webjars/**",
                        "/doc.html",
                        "/swagger-resources/**",
                        "/v3/api-docs/**",
                        "/swagger-ui/**",
                        "/swagger-ui.html",
                        "/ws/**",
                        "/ws-app/**"
                );
    }

    /**
     * Password encoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Manually inject AuthenticationManager
     *
     * @param authenticationConfiguration Authentication configuration
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
