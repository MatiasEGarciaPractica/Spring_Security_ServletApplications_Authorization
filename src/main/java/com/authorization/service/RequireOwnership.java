package com.authorization.service;

import org.springframework.security.access.prepost.PostAuthorize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//to the method which I add this, will have added the PostAuthorized
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@PostAuthorize("returnObject.username == authentication.name")
public @interface RequireOwnership {}
