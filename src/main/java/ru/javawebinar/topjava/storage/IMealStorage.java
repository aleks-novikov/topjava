package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.util.Map;

public interface IMealStorage {

    void add(Meal meal);

    Meal get(Integer id);

    void update(Integer id, Meal meal);

    void delete(Integer id);

    Map<Integer, MealTo> getAll();
}
