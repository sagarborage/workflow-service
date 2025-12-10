package com.sowermate.security.config;

import com.sowermate.security.handlers.CustomResponseHeaderWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * The CORS configuration.
 *
 * @author sborage
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    // Inject the cors.allowedOrigins property into the origins list
    @Value("#{'${cors.allowedOrigins}'.split(',')}")
    private String[] allowedOrigins;

    @Value("#{'${cors.allowedMethods}'.split(',')}")
    private String[] allowedMethods;

    @Value("#{'${cors.allowedHeaders}'.split(',')}")
    private String[] allowedHeaders;

    @Value("#{${cors.allowCredentials}}")
    private boolean allowCredentials;
    @Value("#{${cors.maxAge}}")
    private Long maxAge;

    @Override
    public void addCorsMappings(CorsRegistry registry) {

        registry.addMapping("/**")
            .allowedOrigins(allowedOrigins)
            .allowedMethods(allowedMethods)
            .allowedHeaders(allowedHeaders)
            .allowCredentials(allowCredentials)
            .maxAge(maxAge);

    }

    @Bean
    public CustomResponseHeaderWriter customResponseHeaderWriter() {
        return new CustomResponseHeaderWriter(allowedOrigins, allowedMethods, allowedHeaders, allowCredentials, maxAge);
    }
}
