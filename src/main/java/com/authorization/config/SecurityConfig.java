package com.authorization.config;

import com.authorization.customAuthorizations.CustomEnterAuthorizationManager;
import com.authorization.customAuthorizations.CustomResultAuthorizationManager;
import org.springframework.aop.Advisor;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authorization.method.AuthorizationManagerAfterMethodInterceptor;
import org.springframework.security.authorization.method.AuthorizationManagerBeforeMethodInterceptor;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;

import java.util.List;

@Configuration
@EnableWebSecurity
//added the prePostEnabled to use CustomEnterAuthorizationManager and CustomResultAuthorizationManager,
//spring has its own implementation, so I need to disable it.
@EnableMethodSecurity(prePostEnabled = false)
public class SecurityConfig {

    @Bean("customPreAuthorize")
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    Advisor preAuthorize(CustomEnterAuthorizationManager manager){
        return AuthorizationManagerBeforeMethodInterceptor.preAuthorize(manager);
    }

    @Bean("customPostAuthorize")
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    Advisor postAuthorize(CustomResultAuthorizationManager manager){
        return AuthorizationManagerAfterMethodInterceptor.postAuthorize(manager);
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable); //without this the client cannot enter login method.
        http.authorizeHttpRequests(authorize -> authorize.requestMatchers("/auth/login").permitAll()
                .anyRequest().authenticated());
        return http.build();
    }

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
