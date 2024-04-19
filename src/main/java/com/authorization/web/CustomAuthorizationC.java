package com.authorization.web;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customAuthorization")
public class CustomAuthorizationC {

    @PreAuthorize("@authz.onlyAdmin(#root)")
    @GetMapping("/admin/SpEL")
    public ResponseEntity<String> adminCustomAuthorization(){
        return ResponseEntity.ok("Custom authorization with SpEl works correctly, Mr.Admin");
    }
}
