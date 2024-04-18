package com.authorization.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserC {

    @GetMapping(value = "/user")
    public ResponseEntity<String> user(){
        return ResponseEntity.ok("hello user");
    }
}
