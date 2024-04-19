package com.authorization.web;

import com.authorization.dto.LoginRequest;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthC {

    private final AuthenticationManager authManager;
    private final SecurityContextRepository securityContextRepository;
    private final SecurityContextHolderStrategy securityContextHolderStrategy;

    public AuthC(AuthenticationManager authManager ,
                      SecurityContextRepository securityContextRepository,
                      SecurityContextHolderStrategy securityContextHolderStrategy){
        this.authManager = authManager;
        this.securityContextRepository = securityContextRepository;
        this.securityContextHolderStrategy = securityContextHolderStrategy;
    }

    @PostMapping(value = "/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest, HttpServletRequest request, HttpServletResponse response){
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password())
        );
        SecurityContext context = securityContextHolderStrategy.createEmptyContext();
        context.setAuthentication(authentication);
        securityContextHolderStrategy.setContext(context);
        securityContextRepository.saveContext(context, request, response);
        return ResponseEntity.ok("Welcome, you have successfully log in");
    }
    @PostMapping(value = "/closeSession") // i don't know why, but if I use the value = "/logout" , doesn't matter wich mapping I use always I get "method GET not supported"
    public ResponseEntity<String> logout(HttpServletRequest request) throws ServletException {
        request.logout();
        return ResponseEntity.ok("Logout successfully");
    }


}
