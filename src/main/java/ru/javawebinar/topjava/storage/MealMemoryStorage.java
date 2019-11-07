package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MealMemoryStorage implements MealStorage {

    private Map<Integer, Meal> mealsMap = new ConcurrentHashMap<>();
    private AtomicInteger idsFactory = new AtomicInteger(0);

    public MealMemoryStorage() {
        MealsUtil.DEFAULT_MEALS.forEach(this::add);
    }

    @Override
    public void add(Meal meal) {
        meal.setId(idsFactory.incrementAndGet());
        mealsMap.put(meal.getId(), meal);
    }

    @Override
    public Meal get(Integer id) {
        return mealsMap.get(id);
    }

    @Override
    public void update(Integer id, Meal updatedMeal) {
        updatedMeal.setId(id);
        mealsMap.put(id, updatedMeal);
    }

    @Override
    public boolean delete(Integer id) {
      return mealsMap.remove(id) != null;
    }

    @Override
    public  Map<Integer, Meal> getAll() {
        return mealsMap;
    }
}
