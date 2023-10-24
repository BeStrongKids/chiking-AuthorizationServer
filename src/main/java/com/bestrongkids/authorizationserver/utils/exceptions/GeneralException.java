package com.bestrongkids.authorizationserver.utils.exceptions;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class GeneralException {
    public static <T> T throwIfNotFound(Optional<T> optional, String message) {
        return optional.orElseThrow(() -> new UsernameNotFoundException(message));
    }
}
