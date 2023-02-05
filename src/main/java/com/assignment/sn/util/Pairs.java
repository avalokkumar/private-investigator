package com.assignment.sn.util;

public class Pairs<T, U> {
    private final T first;
    private final U second;

    public Pairs(T first, U second) {
        this.first = first;
        this.second = second;
    }

    public T getFirst() {
        return first;
    }

    public U getSecond() {
        return second;
    }
}