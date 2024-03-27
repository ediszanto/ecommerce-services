package com.szanto.apigateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Flux;

import java.util.stream.Stream;

import static com.szanto.apigateway.config.SecurityRole.ADMIN;
import static com.szanto.apigateway.config.SecurityRole.CLIENT;

@Configuration
@EnableWebFluxSecurity
public class WebSecurityConfiguration {

    @Bean
    SecurityWebFilterChain filterChain(ServerHttpSecurity http) {
        ServerHttpSecurity serverHttpSecurity = http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(authorize -> authorize
                        .pathMatchers(HttpMethod.GET, "/product/**").hasAnyRole(CLIENT.name(), ADMIN.name())
                        .pathMatchers(HttpMethod.POST, "/product/**").hasRole(ADMIN.name())
                        .pathMatchers(HttpMethod.PUT, "/product/{productId}/**").hasRole(ADMIN.name())
                        .pathMatchers(HttpMethod.DELETE, "/product/{productId}/**").hasRole(ADMIN.name())
                        .pathMatchers(HttpMethod.GET, "/product/{productId}/**").hasAnyRole(CLIENT.name(), ADMIN.name())
                        .pathMatchers("/product/decreaseQuantity/**").hasRole(ADMIN.name())

                        .pathMatchers("/order").hasAnyRole(ADMIN.name(), CLIENT.name())
                        .pathMatchers("/order/{orderId}").hasAnyRole(ADMIN.name(), CLIENT.name())

                        .anyExchange().authenticated()
                )
                .oauth2ResourceServer(oauth2ResourceServer -> oauth2ResourceServer
                        .jwt(Customizer.withDefaults()));
        return serverHttpSecurity.build();
    }

    @Bean
    public ReactiveJwtAuthenticationConverter jwtAuthenticationConverter() {
        ReactiveJwtAuthenticationConverter converter = new ReactiveJwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(jwt ->
                        Flux.fromStream(
                                Stream.of(
                                        new SimpleGrantedAuthority(getRole(jwt)),
                                        new SimpleGrantedAuthority("USER_ID_" + jwt.getClaimAsString("sub"))
                                )
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
