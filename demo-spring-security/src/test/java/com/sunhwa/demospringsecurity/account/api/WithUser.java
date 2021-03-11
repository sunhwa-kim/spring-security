package com.sunhwa.demospringsecurity.account.api;

import org.springframework.security.test.context.support.WithMockUser;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithMockUser(username = "sunwha", roles = "USER")
public @interface WithUser {
}
