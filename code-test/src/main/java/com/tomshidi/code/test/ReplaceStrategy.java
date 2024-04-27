package com.tomshidi.code.test;

/**
 * @author TomShiDi
 * @since 2024/4/27 16:56
 */
@FunctionalInterface
public interface ReplaceStrategy {
    /**
     * implement different character replacement strategies.
     *
     * @param origin source string
     * @param target The string to be replaced
     * @return The replaced character
     */
    String replace(String origin, String target);
}
