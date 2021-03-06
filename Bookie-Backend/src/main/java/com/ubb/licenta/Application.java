package com.ubb.licenta;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableAutoConfiguration
@ComponentScan
@SpringBootApplication
@EnableCaching
public class Application {

    @Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials( true );
        config.addAllowedOrigin( "*" );
        config.addAllowedHeader( "*" );
        config.addAllowedMethod( "OPTIONS" );
        config.addAllowedMethod( "HEAD" );
        config.addAllowedMethod( "GET" );
        config.addAllowedMethod( "PUT" );
        config.addAllowedMethod( "POST" );
        config.addAllowedMethod( "DELETE" );
        config.addAllowedMethod( "PATCH" );
        source.registerCorsConfiguration( "/**", config );

        return new CorsFilter( source );
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public static void main( String[] args ) {
        SpringApplication.run( Application.class, args );
    }
}
