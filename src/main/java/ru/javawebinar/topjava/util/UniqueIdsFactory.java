package ru.javawebinar.topjava.util;

import java.util.concurrent.atomic.AtomicInteger;

public enum UniqueIdsFactory {
    INSTANCE;

    private AtomicInteger factory = new AtomicInteger(0);

    public Integer incrementAndGet() {
        return factory.incrementAndGet();
    }
}
