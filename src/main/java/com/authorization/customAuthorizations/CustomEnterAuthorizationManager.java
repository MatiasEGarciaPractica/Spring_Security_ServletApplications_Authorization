package com.authorization.customAuthorizations;

import com.authorization.config.Roles;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

/**
 * Only client with role user
 */
@Component
public class CustomEnterAuthorizationManager implements AuthorizationManager<MethodInvocation>{

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, MethodInvocation invocation) {
        Authentication auth = authentication.get();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        for (GrantedAuthority g : userDetails.getAuthorities()){
            if(g.getAuthority().equals(Roles.USER.getAsRole())) return new AuthorizationDecision(true);
        }
        return new AuthorizationDecision(false);
    }
}
