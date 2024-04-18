package com.authorization.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class ExctractValueC {

    //to be able to enter, name has to be the same than the authenticated user's name.
    @GetMapping(value = "/extractValue/{name}")
    public ResponseEntity<String> extractPathValue(@PathVariable(value = "name") String name) {
        return ResponseEntity.ok("value was extracted successfully, you user name is : " + name);
    }
}
