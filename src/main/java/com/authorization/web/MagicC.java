package com.authorization.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class MagicC {

    @GetMapping(value = "/IKnowTheMagicWords")
    public ResponseEntity<String> openSesame(){
        return ResponseEntity.ok("Congratulations, you may enter");
    }

}
