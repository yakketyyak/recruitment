package com.recruitment.casttest.utils;

import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.net.URI;

public final class ControllerUtils {

    private ControllerUtils() {
        throw new UnsupportedOperationException("ControllerUtils amy not be instantiated");
    }

    public static URI buildMvcPathComponent(final String pathValue, final Class<?> clazz) {
        return MvcUriComponentsBuilder
                .fromController(clazz)
                .path("/{id}")
                .buildAndExpand(pathValue)
                .toUri();
    }
}
