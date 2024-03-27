package com.szanto.orderservice.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

import static com.szanto.orderservice.configuration.SecurityRole.ADMIN;
import static com.szanto.orderservice.configuration.SecurityRole.CLIENT;

@Configuration
@EnableMethodSecurity
public class WebSecurityConfiguration {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/order").hasAnyRole(ADMIN.name(), CLIENT.name())
                        .requestMatchers("/order/{orderId}").hasAnyRole(ADMIN.name(), CLIENT.name())

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
