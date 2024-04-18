package com.authorization.web;

import com.authorization.dto.LoginRequest;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class Controller {

    private final AuthenticationManager authManager;
    private final SecurityContextRepository securityContextRepository;
    private final SecurityContextHolderStrategy securityContextHolderStrategy;

    public Controller(AuthenticationManager authManager ,
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

    @GetMapping(value = "/admin")
    public  ResponseEntity<String> admin(){
        return ResponseEntity.ok("admin endpoint ok");
    }

    @GetMapping(value = "/employee")
    public  ResponseEntity<String> employee(){
        return ResponseEntity.ok("employee endpoint ok");
    }

    @GetMapping(value = "/user")
    public ResponseEntity<String> user(){
        return ResponseEntity.ok("user endpoint ok");
    }

}
