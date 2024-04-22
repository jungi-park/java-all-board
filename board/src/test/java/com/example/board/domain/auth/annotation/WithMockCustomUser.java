package com.example.board.domain.auth.annotation;

import org.springframework.security.test.context.support.WithSecurityContext;

@WithSecurityContext(factory = WithMockCustomUserSecurityContextFactory.class)
public @interface WithMockCustomUser {
    String userId() default "qmqqqm";

    String role() default "USER";
}
