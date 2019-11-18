package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

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
            return meal;
        }

        return userMeals.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
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
        return sortByDate(new ArrayList<>(getUserMeals(userId).values()));
    }

    @Override
    public List<Meal> getAllByDate(int userId, LocalDate startDate, LocalDate endDate) {
        return sortByDate(MealsUtil.filterByDateTime(getUserMeals(userId).values(), startDate, endDate));
    }

    private List<Meal> sortByDate(List<Meal> meals) {
        return meals.stream().sorted(Comparator.comparing(Meal::getDateTime)
                             .reversed()).collect(Collectors.toList());
    }

    private Map<Integer, Meal> getUserMeals(int userId) {
        return repository.computeIfAbsent(userId, id -> new LinkedHashMap<>());
    }
}

