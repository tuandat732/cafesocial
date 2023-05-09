package com.example.cafesocial.core.config;

@FunctionalInterface
public interface HandlerCallback {
    public void handle(Object response, Throwable throwable);
}
