package com.authorization.customAuthorizations;

import com.authorization.config.Roles;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 * We authorize the client with SpEl.
 */
@Component("authz")
public class AuthorizationLogicWithSpEL {
    //only admin can pass.
    public boolean onlyAdmin(MethodSecurityExpressionOperations operations){
        UserDetails userDetails = (UserDetails) operations.getAuthentication().getPrincipal();
        for(GrantedAuthority authority : userDetails.getAuthorities()){
            if(authority.getAuthority().equals( Roles.ADMIN.getAsRole() )) return true;
        }
        return false;
    }
}
