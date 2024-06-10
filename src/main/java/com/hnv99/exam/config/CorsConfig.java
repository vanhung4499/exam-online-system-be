package com.hnv99.exam.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Collections;

@Configuration
public class CorsConfig {

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        // 1. Allow any origin
        corsConfiguration.setAllowedOriginPatterns(Collections.singletonList("*"));
        // 2. Allow any request headers
        corsConfiguration.addAllowedHeader(CorsConfiguration.ALL);
        // 3. Allow any methods
        corsConfiguration.addAllowedMethod(CorsConfiguration.ALL);
        // 4. Allow credentials
        corsConfiguration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        CorsFilter corsFilter = new CorsFilter(source);

        FilterRegistrationBean<CorsFilter> filterRegistrationBean = new FilterRegistrationBean<>(corsFilter);
        filterRegistrationBean.setOrder(-101);  // Less than SpringSecurity Filter's Order(-100)

        return filterRegistrationBean;
    }
}
