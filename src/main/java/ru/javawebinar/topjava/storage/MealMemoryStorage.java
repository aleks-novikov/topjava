package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MealMemoryStorage implements IMealStorage {

    private Map<Integer, MealTo> mealsMap;

    private Map<Integer, MealTo> getDefaultMeals() {
         return new ConcurrentHashMap<>(MealsUtil.getFiltered(Arrays.asList(
                new Meal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new Meal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new Meal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new Meal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new Meal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new Meal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)),
                LocalTime.MIN, LocalTime.MAX, 2000));
    }

    public Map<Integer, MealTo> getMealsMap() {
        if (mealsMap == null) {
            mealsMap =  new ConcurrentHashMap<>(getDefaultMeals());
        }
        return mealsMap;
    }

    @Override
    public boolean add(LocalDateTime dateTime, String description, int calories, boolean excess) {
        return true;
    }

    @Override
    public MealTo get(Integer id) {
        return mealsMap.get(id);
    }

    @Override
    public boolean update(Integer id, LocalDateTime dateTime, String description, int calories, boolean excess) {
        return true;
    }

    @Override
    public boolean delete(Integer id) {
        return true;
    }
}
