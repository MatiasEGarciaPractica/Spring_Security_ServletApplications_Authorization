package com.authorization.config;

import jakarta.servlet.http.HttpSession;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.security.web.header.Header;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

@Component
public final class OpenPolicyAgentAuthorizationmanager implements AuthorizationManager<RequestAuthorizationContext> {


    //IF CLIENT has header Membership == old can go, otherwise cannot.
    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext object) {
         String membership = object.getRequest().getHeader("Membership");
         if(membership != null && membership.equalsIgnoreCase("old")) {
             return new AuthorizationDecision(true);
         }else {
             return new AuthorizationDecision(false);
         }
    }
}
