package com.jddev.velemenyezz.shared.config;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeycloakConfig {

    @Value("${spring.keycloak.admin.clientId")
    private String clientId;
    @Value("${spring.keycloak.admin.clientSecret")
    private String clientSecret;
    @Value("${spring.keycloak.realm")
    private String keycloakRealm;
    @Value("${spring.keycloak.serverUrl")
    private String keycloakURL;

    @Bean
    public Keycloak keycloak(){
        return KeycloakBuilder.builder()
                .clientSecret(clientSecret)
                .clientId(clientId)
                .grantType("client_credentials")
                .realm(keycloakRealm)
                .serverUrl(keycloakURL)
                .build();
    }
}
