package com.authorization.customAuthorizations;

import com.authorization.config.Roles;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.authorization.method.MethodInvocationResult;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

/**
 * Only employee users can get the result
 */
@Component
public class CustomResultAuthorizationManager implements AuthorizationManager<MethodInvocationResult> {

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, MethodInvocationResult object) {
        String result;
        Authentication auth;
        UserDetails userDetails;

        result = (String)object.getResult();
        System.out.println("The result was : " + result);
        auth = authentication.get();
        userDetails = (UserDetails) auth.getPrincipal();
        for(GrantedAuthority g : userDetails.getAuthorities()){
            if(g.getAuthority().equals(Roles.EMPLOYEE.getAsRole())) return new AuthorizationDecision(true);
        }
        return new AuthorizationDecision(false);
    }
}
