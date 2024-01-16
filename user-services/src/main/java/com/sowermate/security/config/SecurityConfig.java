package com.sowermate.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sowermate.security.filters.JwtAuthenticationFilter;
import com.sowermate.security.filters.JwtAuthorizationFilter;
import com.sowermate.security.handlers.AuthenticationFailureHandler;
import com.sowermate.security.handlers.CustomResponseHeaderWriter;
import com.sowermate.security.handlers.TokenAuthenticationSuccessHandler;
import com.sowermate.security.services.CustomTokenService;
import com.sowermate.security.services.CustomUserDetailsService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.text.SimpleDateFormat;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private CustomTokenService customTokenService;

    @Autowired
    private JwtConfig jwtConfig;

    @Autowired
    private UrlConfig urlConfig;

    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;

    @Autowired
    private CustomResponseHeaderWriter responseHeaderWriter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // @formatter:off
        http
                .authorizeHttpRequests((authorize) ->
                        authorize.requestMatchers(urlConfig.getUnprotectedUrls()).permitAll()
                                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                                .anyRequest().authenticated())
                //.csrf((csrf) -> csrf.ignoringRequestMatchers(urlConfig.getUnprotectedUrls()))
                .csrf(AbstractHttpConfigurer::disable)
                //.httpBasic(Customizer.withDefaults())
                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilter(new JwtAuthenticationFilter(customUserDetailsService, tokenAuthenticationSuccessHandler(), bCryptPasswordEncoder(), objectMapper(), authenticationFailureHandler()))
                .addFilter(new JwtAuthorizationFilter(authenticationManager(authenticationConfiguration), jwtConfig.getSecret(), urlConfig, authenticationFailureHandler(), customTokenService));
        //http.httpBasic(Customizer.withDefaults());
        // @formatter:on
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfiguration) throws Exception {
        return authConfiguration.getAuthenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new AuthenticationFailureHandler(objectMapper(), this.responseHeaderWriter);
    }

    @Bean
    public TokenAuthenticationSuccessHandler tokenAuthenticationSuccessHandler() {
        return new TokenAuthenticationSuccessHandler(customTokenService, objectMapper(), responseHeaderWriter);
    }


    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        // Configure the date format
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        objectMapper.setDateFormat(dateFormat);

        // Configure to write dates as strings
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        return objectMapper;
    }

    @Bean
    public ModelMapper ModelMapper() {
        return new ModelMapper();
    }
}
