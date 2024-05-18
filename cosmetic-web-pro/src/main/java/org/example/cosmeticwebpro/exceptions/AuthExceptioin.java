package org.example.cosmeticwebpro.exceptions;

import javax.naming.AuthenticationException;

public class AuthExceptioin extends AuthenticationException {
    public AuthExceptioin(String msg, Throwable t) {
        super(msg);
    }

    public AuthExceptioin(String msg) {
        super(msg);
    }
}
