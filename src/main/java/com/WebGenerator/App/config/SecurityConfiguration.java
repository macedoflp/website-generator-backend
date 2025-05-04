package com.WebGenerator.App.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfiguration {

    public static final String[] ENDPOINTS_WITH_AUTHENTICATION_NOT_REQUIRED = {
            "/register",
            "/login/request-code",
            "/login/validate-code",
            "/payment/create-checkout-session",
            "/payment/create-pix-payment",
            "/users/",
            "/websites/",
            "/websites/get-img/8",
            "/websites/search-music",
            "/error", // Adicionando o endpoint de erro
            "/favicon.ico", // Opcional: para evitar problemas com o ícone
            "/webjars/**",  // Opcional: para lidar com dependências de bibliotecas estáticas
            "/resources/**", // Opcional: para recursos estáticos
            "/websites/get-img/8"
    };

    public static final String[] ENDPOINTS_WITH_AUTHENTICATION_REQUIRED = {
            "/websites"
    };

    public static final String[] ENDPOINTS_CUSTOMER = {
            "/users/test/customer",
            "/websites",
    };

    public static final String[] ENDPOINTS_ADMIN = {
            "/users/administrator"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, UserAuthenticationFilter userAuthenticationFilter) throws Exception {
        return http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().authorizeHttpRequests()
                .requestMatchers(ENDPOINTS_WITH_AUTHENTICATION_NOT_REQUIRED).permitAll()
                .requestMatchers(ENDPOINTS_WITH_AUTHENTICATION_REQUIRED).authenticated()
                .requestMatchers(ENDPOINTS_CUSTOMER).hasRole("CUSTOMER")
                .requestMatchers(ENDPOINTS_ADMIN).hasRole("ADMINISTRATOR")
                .anyRequest().denyAll()
                .and().addFilterBefore(userAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
