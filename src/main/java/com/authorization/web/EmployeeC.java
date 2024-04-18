package com.authorization.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employee")
public class EmployeeC {

    @GetMapping(value = "/hello")
    public ResponseEntity<String> hello(){
        return ResponseEntity.ok("hello employee from method GET");
    }

    @PostMapping(value = "/helloAdmin")
    public ResponseEntity<String> helloAdmin(){
        return ResponseEntity.ok("hello admin from method POST");
    }
}
