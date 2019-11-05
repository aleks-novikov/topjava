package ru.javawebinar.topjava.model;

import java.time.LocalDateTime;

public class Meal {
    private Integer id;
    private final LocalDateTime dateTime;
    private final String description;

    private final int calories;

    public Meal(LocalDateTime dateTime, String description, int calories) {
        id = UniqueIdsFactory.INSTANCE.incrementAndGet();
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    public Integer getId() {
        return id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }
}
