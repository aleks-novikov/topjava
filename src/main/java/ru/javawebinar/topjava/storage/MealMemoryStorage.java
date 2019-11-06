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

public class MealMemoryStorage implements IMealStorage {

    private Map<Integer, Meal> mealsMap;

    private void getDefaultMeals() {
        mealsMap = new ConcurrentHashMap<>();
        Arrays.asList(new Meal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                      new Meal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                      new Meal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                      new Meal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                      new Meal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                      new Meal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510))
                      .forEach(meal -> mealsMap.put(meal.getId(), meal));
    }

    @Override
    public void add(Meal newMeal) {
        mealsMap.put(newMeal.getId(), newMeal);
    }

    @Override
    public Meal get(Integer id) {
        return mealsMap.get(id);
    }

    @Override
    public void update(Integer id, Meal updatedMeal) {
        Meal meal = mealsMap.get(id);
        meal.setDateTime(updatedMeal.getDateTime());
        meal.setDescription(updatedMeal.getDescription());
        meal.setCalories(updatedMeal.getCalories());
        mealsMap.put(id, meal);
    }

    @Override
    public void delete(Integer id) {
        mealsMap.computeIfPresent(id, (mealId, meal) -> mealsMap.remove(mealId));
    }

    @Override
    public  Map<Integer, MealTo> getAll() {
        if (mealsMap == null) {
            getDefaultMeals();
        }
        return MealsUtil.getFiltered(new ArrayList<>(mealsMap.values()), LocalTime.MIN, LocalTime.MAX, 2000);
    }
}
