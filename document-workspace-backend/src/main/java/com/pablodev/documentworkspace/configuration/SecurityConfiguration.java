package com.pablodev.documentworkspace.configuration;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class SecurityConfiguration {

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*"); // Permitir cualquier origen
        corsConfiguration.addAllowedMethod("*"); // Permitir cualquier método HTTP
        corsConfiguration.addAllowedHeader("*"); // Permitir cualquier encabezado
        corsConfiguration.setAllowCredentials(true); // Permitir el uso de credenciales

        // Configurar las rutas que aceptarán las configuraciones CORS
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);

        return new CorsFilter(source);

    }

}
