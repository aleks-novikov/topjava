package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private Map<Integer, Map<Integer, Meal>> repository = new HashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        SecurityUtil.setAuthUserId(2);
        MealsUtil.USER_2_MEALS.forEach(this::save);
        SecurityUtil.setAuthUserId(1);
        MealsUtil.USER_1_MEALS.forEach(this::save);
    }

    @Override
    public Meal save(Meal meal) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            getUserMeals().put(meal.getId(), meal);

            if (getUserMeals().values().size() > 1)
                sortMeals();
            return meal;
        }
        
        Meal updatedMeal = getUserMeals().computeIfPresent(meal.getId(), (id, oldMeal) -> meal);

        if (getUserMeals().values().size() > 1)
            sortMeals();
        return updatedMeal;
    }

    @Override
    public boolean delete(int id) {
       return getUserMeals().remove(id) != null;
    }

    @Override
    public Meal get(int id) {
        return getUserMeals().get(id);
    }

    @Override
    public Collection<Meal> getAll() {
        return getUserMeals().values();
    }

    private Map<Integer, Meal> getUserMeals() {
        return repository.computeIfAbsent(
                SecurityUtil.authUserId(), id -> new LinkedHashMap<>());
    }
    
    private void sortMeals() {
        List<Meal> sortedMeals = MealsUtil.sortByDate(getUserMeals().values());
        getUserMeals().clear();
        sortedMeals.forEach(meal -> getUserMeals().put(meal.getId(), meal));
    }
}

