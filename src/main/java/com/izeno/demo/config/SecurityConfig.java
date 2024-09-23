package com.izeno.demo.config;

import com.izeno.demo.entity.OAuthUser;
import com.izeno.demo.services.OAuthUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import java.util.Map;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomAuthenticationProvider customAuthenticationProvider;

    @Value("${oauth2.client.registration.default-client.authorization-uri}")
    private String authorizationUri;

    @Value("${oauth2.client.registration.default-client.token-uri}")
    private String tokenUri;

    @Value("${oauth2.client.registration.default-client.redirect-uri}")
    private String redirectUri;

    @Value("${oauth2.client.registration.default-client.scope}")
    private String scope;

    @Value("${oauth2.client.registration.default-client.user-info-uri}")
    private String userInfoUri;

    @Value("${oauth2.client.registration.default-client.user-name-attribute}")
    private String userNameAttribute;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(outh -> {
                outh.requestMatchers("/api/v1/policy/status/**").authenticated();
                outh.anyRequest().permitAll();
            })
            .oauth2Login(withDefaults())
            .formLogin(withDefaults())
            .csrf(csrf -> csrf.disable());

        return http.build();
    }

    @Autowired
    public void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(customAuthenticationProvider);
    }

    @Autowired
    private OAuthUserService oauthClientService;

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        return registrationId -> {
            OAuthUser client = oauthClientService.getClientCredentials(registrationId);

            return ClientRegistration.withRegistrationId("default-client")
                    .clientId(client.getClientId())
                    .clientSecret(client.getClientSecret())
                    .authorizationUri(authorizationUri)
                    .tokenUri(tokenUri)
                    .redirectUri(redirectUri)
                    .scope(scope)
                    .providerConfigurationMetadata(Map.of(
                        "authorization_endpoint", authorizationUri,
                        "token_endpoint", tokenUri,
                        "user_info_endpoint", userInfoUri,
                        "user_name_attribute", userNameAttribute
                    ))
                    .build();
        };
    }
}
