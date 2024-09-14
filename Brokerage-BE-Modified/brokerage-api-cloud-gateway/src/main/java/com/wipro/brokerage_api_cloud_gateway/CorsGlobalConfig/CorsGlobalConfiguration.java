//package com.wipro.brokerage_api_cloud_gateway.CorsGlobalConfig;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//import org.springframework.web.filter.CorsFilter;
//import java.util.Arrays;
//
//
//    @Configuration
//    public class CorsGlobalConfiguration {
//        @Bean
//        public CorsFilter corsFilter() {
//            UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//            CorsConfiguration config = new CorsConfiguration();
//            // Allow credentials (for cookies or tokens)
//            config.setAllowCredentials(true);
//            // Allow specific origins (change to your frontend URL)
//            config.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
//            // Allow specific HTTP methods
//            config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
//            // Allow specific headers
//            config.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
//            source.registerCorsConfiguration("/**", config);
//            return new CorsFilter(source);
//        }
//    }
//
