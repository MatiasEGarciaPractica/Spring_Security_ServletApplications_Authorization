package com.authorization.config;

import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component("authz")
public class AuthorizationLogicWithSpEL {
    public boolean onlyAdmin(MethodSecurityExpressionOperations operations){
        UserDetails userDetails = (UserDetails) operations.getAuthentication().getPrincipal();
        for(GrantedAuthority authority : userDetails.getAuthorities()){
            if(authority.getAuthority().equals( Roles.ADMIN.getAsRole() )) return true;
        }
        return false;
    }
}
