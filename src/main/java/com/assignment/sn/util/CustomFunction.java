package com.assignment.sn.util;

import java.util.Objects;

@FunctionalInterface
public interface CustomFunction<S, T, U, R> {

    R apply(S s, T t, U u);
}

