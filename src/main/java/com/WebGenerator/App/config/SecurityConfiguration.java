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
            "/login/request-code",// ok
            "/login",
            "/login/validate-code",// ok
            "/payment/create-checkout-session",
            "/payment/create-pix-payment",
            "/users/",// ok
            "/websites/",// ok
            "/websites/get-img",
            "/websites/add-img",
            "/websites/search-music",
            "/error", // Adicionando o endpoint de erro
            "/favicon.ico", // Opcional: para evitar problemas com o ícone
            "/webjars/**",  // Opcional: para lidar com dependências de bibliotecas estáticas
            "/resources/**", // Opcional: para recursos estáticos
    };

    public static final String[] ENDPOINTS_WITH_AUTHENTICATION_REQUIRED = {
            "/websites",//ok o usuario não consegue criar um site sem esta autenticado
            "/links-user/"
    };

    public static final String[] ENDPOINTS_CUSTOMER = {
            "/websites",
    };

    public static final String[] ENDPOINTS_ADMIN = {
//            "/users/administrator"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, UserAuthenticationFilter userAuthenticationFilter) throws Exception {
        return http.cors().and().csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().authorizeHttpRequests()
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
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
