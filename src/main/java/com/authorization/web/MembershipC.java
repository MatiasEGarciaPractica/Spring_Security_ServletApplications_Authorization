package com.authorization.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/membership")
public class MembershipC {

    @GetMapping
    public ResponseEntity<String> hello(){
        return ResponseEntity.ok("hello old member of this app");
    }

}
