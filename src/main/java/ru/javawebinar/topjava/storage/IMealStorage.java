package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.MealTo;

import java.time.LocalDateTime;

public interface IMealStorage {

    boolean add(LocalDateTime dateTime, String description, int calories, boolean excess);

    MealTo get(Integer id);

    boolean update(Integer id, LocalDateTime dateTime, String description, int calories, boolean excess);

    boolean delete(Integer id);
}
