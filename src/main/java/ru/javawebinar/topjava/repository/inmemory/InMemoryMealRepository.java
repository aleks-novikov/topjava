package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private Map<Integer, Map<Integer, Meal>> repository = new HashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.USER_1_MEALS.forEach(meal -> save(1, meal));
        MealsUtil.USER_2_MEALS.forEach(meal -> save(2, meal));
    }

    @Override
    public Meal save(int userId, Meal meal) {
        Map<Integer, Meal> userMeals = getUserMeals(userId);

        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            userMeals.put(meal.getId(), meal);

            if (userMeals.values().size() > 1)
                sortMeals();
            return meal;
        }

        Meal updatedMeal = userMeals.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
        if (userMeals.values().size() > 1)
            sortMeals();
        return updatedMeal;
    }

    @Override
    public boolean delete(int userId, int mealId) {
       return repository.get(userId).remove(mealId) != null;
    }

    @Override
    public Meal get(int userId, int mealId) {
        return repository.get(userId).get(mealId);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return new ArrayList<>(getUserMeals(userId).values());
    }

    private Map<Integer, Meal> getUserMeals(int userId) {
        return repository.computeIfAbsent(userId, id -> new LinkedHashMap<>());
    }
    
    private void sortMeals() {
        List<Meal> sortedMeals = MealsUtil.sortByDate(getUserMeals(1).values());
        getUserMeals(1).clear();
        sortedMeals.forEach(meal -> getUserMeals(1).put(meal.getId(), meal));
    }

    @Override
    public List<Meal> getAllByDate(LocalDate startDate, LocalDate endDate) {
        return MealsUtil.filterByDate(getAll(1), startDate, endDate);
    }

    @Override
    public List<Meal> getAllByTime(LocalTime startTime, LocalTime endTime) {
        return MealsUtil.filterByTime(getAll(1), startTime, endTime);
    }

    @Override
    public List<Meal> getAllByDateTime(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        return MealsUtil.filterByDateTime(getAll(1), startDate, endDate, startTime, endTime);
    }

    @Override
    public boolean userExists(int userId) {
        return repository.containsKey(userId);
    }
}

