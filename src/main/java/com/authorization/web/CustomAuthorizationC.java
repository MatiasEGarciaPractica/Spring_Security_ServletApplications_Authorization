package com.authorization.web;

import com.authorization.service.CustomAuthenticationImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customAuthorization")
public class CustomAuthorizationC {

    private final CustomAuthenticationImpl customAuthentication;

    public CustomAuthorizationC(CustomAuthenticationImpl customAuthentication){
        this.customAuthentication = customAuthentication;
    }

    @PreAuthorize("@authz.onlyAdmin(#root)")
    @GetMapping("/admin/SpEL")
    public ResponseEntity<String> adminCustomAuthorization(){
        return ResponseEntity.ok("Custom authorization with SpEl works correctly, Mr.Admin");
    }

    //for this to work you need to have @EnableMethodSecurity(prePostEnabled = false) and descoment preAuthorize in SecurityConfig
    @GetMapping("/user/managerAuthentication/canEnterOnMethod")
    public ResponseEntity<String> userPreAuthorizeManagerAuthentication(){
        return  ResponseEntity.ok(customAuthentication.isUser());
    }
    //for this to work you need to have @EnableMethodSecurity(prePostEnabled = false) and descoment postAuthorize in SecurityConfig
    @GetMapping("/employee/managerAuthentication/canGetResult")
    public ResponseEntity<String> userPostAuthorizeManagerAuthentication(){
        return  ResponseEntity.ok(customAuthentication.isEmployee());
    }

}
