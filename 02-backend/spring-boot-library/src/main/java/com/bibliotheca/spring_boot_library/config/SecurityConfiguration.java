package com.bibliotheca.spring_boot_library.config;

import com.okta.spring.boot.oauth.Okta; // Importing Okta for OAuth 2.0 configuration
import org.springframework.context.annotation.Bean; // Importing the Bean annotation
import org.springframework.context.annotation.Configuration; // Importing the Configuration annotation
import org.springframework.security.config.annotation.web.builders.HttpSecurity; // Importing HttpSecurity for configuring web security
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity; // Importing EnableWebSecurity for enabling web security
import org.springframework.security.web.SecurityFilterChain; // Importing SecurityFilterChain for defining security filters
import org.springframework.web.accept.ContentNegotiationStrategy; // Importing ContentNegotiationStrategy for content negotiation
import org.springframework.web.accept.HeaderContentNegotiationStrategy; // Importing HeaderContentNegotiationStrategy for header-based content negotiation

@Configuration // Annotation indicating that this class provides Spring configuration
@EnableWebSecurity // Annotation to enable Spring Security in the application
public class SecurityConfiguration {

    @Bean // Indicates that this method returns a Spring bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // Disable cross-site request forgery (CSRF) protection
        http.csrf().disable();

        // Configure endpoint authorization
        http.authorizeHttpRequests(configurer -> 
            configurer
                .antMatchers("/api/books/secure/**", // Protects endpoints under /api/books/secure
                		"/api/reviews/secure/**", // Protects endpoints under /api/reviews/secure
                		"/api/messages/secure/**", // Protects endpoints under /api/messages/secure
                		"/api/admin/secure/**") // Protects endpoints under /api/admin/secure
                .authenticated() // Require authentication for these endpoints
                .anyRequest().permitAll() // Allow other requests without authentication
        )
        .oauth2ResourceServer(oauth2 -> oauth2.jwt()); // Use JWT for OAuth2 Resource Server

        // Enable CORS (Cross-Origin Resource Sharing) support
        http.cors();

        // Set a content negotiation strategy using header-based negotiation
        http.setSharedObject(ContentNegotiationStrategy.class, new HeaderContentNegotiationStrategy());

        // Configure a friendly non-empty response body for 401 Unauthorized responses
        Okta.configureResourceServer401ResponseBody(http);

        return http.build(); // Build and return the SecurityFilterChain
    }
}
