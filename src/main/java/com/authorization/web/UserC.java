package com.authorization.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserC {

    @GetMapping(value = "/hello")
    public ResponseEntity<String> user(){
        return ResponseEntity.ok("hello user");
    }

    @GetMapping(value = "/eEEEE") //can be accesed by admin role client.
    public ResponseEntity<String> regexRequest(){
        return ResponseEntity.ok("hello from user controller, regex handler");
    }

}
