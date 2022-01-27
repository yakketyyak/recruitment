package com.recruitment.casttest.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Configuration
public class EnableCorsConfiguration {

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration cors = new CorsConfiguration();
        cors.setAllowedOrigins(Collections.singletonList("http//localhost"));
        cors.setAllowedMethods(Arrays.asList(HttpMethod.GET.name(),
                HttpMethod.POST.name(), HttpMethod.DELETE.name(), HttpMethod.OPTIONS.name(),
                HttpMethod.PUT.name()));
        cors.setAllowedHeaders(Collections.singletonList("*"));
        cors.setExposedHeaders(Collections.singletonList("*"));
        cors.setAllowCredentials(true);

        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", cors);
        return source;
    }
}
