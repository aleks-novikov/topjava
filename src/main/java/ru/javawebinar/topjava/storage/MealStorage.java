package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.util.Map;

public interface MealStorage {

    void add(Meal meal);

    Meal get(Integer id);

    void update(Integer id, Meal meal);

    boolean delete(Integer id);

    Map<Integer, Meal> getAll();
}
