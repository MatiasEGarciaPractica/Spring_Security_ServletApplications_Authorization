package com.authorization.service;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class CustomAuthenticationImpl {
    @PreAuthorize("@customPreAuthorize.verify(authentication, isUser)")
    public String isUser(){
        return "On this method can only enter user client, if you are seeing this message, you're authenticated as user.";
    }

    @PostAuthorize("@customPostAuthorize.verify(authentication, isEmployee)")
    public String isEmployee(){
        return "The result of this method only can see it the Employee client, if you are seeing this message, you're authenticated as Employee.";
    }


}
