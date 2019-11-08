package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.util.Collection;

public interface MealStorage {

    void add(Meal meal);

    Meal get(Integer id);

    void update(Integer id, Meal meal);

    boolean delete(Integer id);

    Collection<Meal> getAll();
}
