package com.authorization.service;

import com.authorization.dto.Account;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
@PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE', 'USER')") //can use in interfaces too.
public class ActionServiceImpl {
    @PreAuthorize("hasRole('ADMIN')")
    @PostAuthorize("returnObject.username == authentication.name")
    public Account adminBasicAction(){
        System.out.println("I will realize the action to the admin");
        return new Account("admin", "Hello admin, the action was realized");
    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    @RequireOwnership
    public Account employeeBasicAction(){
        System.out.println("I will realize the action to the employee");
        return new Account("employee", "Hello employee, the action was realized");
    }

    //will not pass post authorization, wrong account username
    @PreAuthorize("hasRole('USER')")
    @PostAuthorize("returnObject.username == authentication.name")
    public Account userBasicAction(){
        System.out.println("I will realize the action to the user");
        return new Account("employee", "Hello user, the action was realized");
    }
}

