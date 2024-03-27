package com.szanto.productservice.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

import static com.szanto.productservice.configuration.SecurityRole.ADMIN;
import static com.szanto.productservice.configuration.SecurityRole.CLIENT;

@Configuration
public class WebSecurityConfiguration {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.GET, "/product/**").hasAnyRole(CLIENT.name(), ADMIN.name())
                        .requestMatchers(HttpMethod.POST, "/product/**").hasRole(ADMIN.name())
                        .requestMatchers(HttpMethod.PUT, "/product/{productId}/**").hasRole(ADMIN.name())
                        .requestMatchers(HttpMethod.DELETE, "/product/{productId}/**").hasRole(ADMIN.name())
                        .requestMatchers(HttpMethod.GET, "/product/{productId}/**").hasAnyRole(CLIENT.name(), ADMIN.name())
                        .requestMatchers("/product/decreaseQuantity/**").hasRole(ADMIN.name())

                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oAuth2ResourceServerConfigurer -> oAuth2ResourceServerConfigurer
                        .jwt(Customizer.withDefaults())
                )
                .build();
    }

    @Bean
    JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(jwt ->
                List.of(
                        new SimpleGrantedAuthority(getRole(jwt)),
                        new SimpleGrantedAuthority("USER_ID_" + jwt.getClaimAsString("sub"))
                )
        );
        return converter;
    }

    private String getRole(Jwt jwt) {
        return "ROLE_" + jwt
                .getClaimAsStringList("permissions")
                .get(0)
                .split(":")[1]
                .toUpperCase();
    }
}

