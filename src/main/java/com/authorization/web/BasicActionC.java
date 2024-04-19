package com.authorization.web;

import com.authorization.service.ActionServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/basicAction")
public class BasicActionC {

    private final ActionServiceImpl actionService;

    public BasicActionC(ActionServiceImpl actionService){
        this.actionService = actionService;
    }

    @GetMapping(value = "/admin")
    public ResponseEntity<String> adminAction(){
        return ResponseEntity.ok(actionService.adminBasicAction().message());
    }

    @GetMapping(value = "/employee")
    public ResponseEntity<String> employeeAction(){
        return ResponseEntity.ok(actionService.employeeBasicAction().message());
    }

    @GetMapping(value = "/user")
    public ResponseEntity<String> userAction(){
        return ResponseEntity.ok(actionService.userBasicAction().message());
    }

}
