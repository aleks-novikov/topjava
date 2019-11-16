package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(this::save);
    }

    @Override
    public Meal save(Meal meal) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            getUserMeals().put(meal.getId(), meal);
            return meal;
        }
        // treat case: update, but not present in storage
        return getUserMeals().computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
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
    public List<Meal> getAll() {
        return new ArrayList<>(getUserMeals().values());
    }

    private Map<Integer, Meal> getUserMeals() {
        return repository.computeIfAbsent(SecurityUtil.authUserId(), k -> new HashMap<>());
    }
}

