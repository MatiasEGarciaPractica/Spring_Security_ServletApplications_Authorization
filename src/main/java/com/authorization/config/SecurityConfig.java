package com.authorization.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


    /**
     * Login using authenticationManager bean.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable); //without this the client cannot enter login method.
        http.authorizeHttpRequests(authorize -> authorize.requestMatchers("/auth/closeSession").authenticated()
                .requestMatchers("/auth/login").permitAll());
        http.authorizeHttpRequests(authorize ->
                authorize.requestMatchers("/admin/**").hasRole(Roles.ADMIN.toString())
                        .requestMatchers(HttpMethod.POST, "/employee/**").hasRole(Roles.ADMIN.name())
                        .requestMatchers(HttpMethod.GET ,"/employee/**").hasRole(Roles.EMPLOYEE.toString())
                        .requestMatchers("/user/hello").hasRole(Roles.USER.toString())
                        .requestMatchers(RegexRequestMatcher.regexMatcher("/user/eE+")).hasRole(Roles.ADMIN.toString()) //only endpoint which match this regular expression
                        .requestMatchers("/extractValue/{name}").access(
                                new WebExpressionAuthorizationManager("#name == authentication.name")
                        ));
        return http.build();
    }


    //beans for authentication
    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        return new ProviderManager(daoAuthenticationProvider);
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder){
        UserDetails admin = User.withUsername("admin")
                .password(passwordEncoder.encode("password"))
                .roles(Roles.ADMIN.toString())
                .build();
        UserDetails employee = User.withUsername("employee")
                .password(passwordEncoder.encode("password"))
                .roles(Roles.EMPLOYEE.toString())
                .build();
        UserDetails user = User.withUsername("user")
                .password(passwordEncoder.encode("password"))
                .roles(Roles.USER.toString())
                .build();
        return new InMemoryUserDetailsManager(List.of(admin, employee, user));
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public SecurityContextRepository securityContextRepository(){
        return new HttpSessionSecurityContextRepository();
    }

    @Bean
    public SecurityContextHolderStrategy securityContextHolderStrategy(){
        return  SecurityContextHolder.getContextHolderStrategy();
    }

}
