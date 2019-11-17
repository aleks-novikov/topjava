package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.time.LocalDate.parse;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;
import static ru.javawebinar.topjava.util.ValidationUtil.checkUserExists;

@Service
public class MealService {

    private final MealRepository repository;

    @Autowired
    public MealService(MealRepository repository) {
        this.repository = repository;
    }

    public Meal create(int userId, Meal meal) {
        checkUserExists(repository.userExists(userId), userId);
        return repository.save(userId, meal);
    }

    public void delete(int userId, int id) throws NotFoundException {
        checkUserExists(repository.userExists(userId), userId);
        checkNotFoundWithId(repository.delete(userId, id), id);
    }

    public Meal get(int userId, int id) throws NotFoundException {
        checkUserExists(repository.userExists(userId), userId);
        return checkNotFoundWithId(repository.get(userId, id), id);
    }

    public List<Meal> getAll(int userId) {
        checkUserExists(repository.userExists(userId), userId);

        Collection<Meal> meals = repository.getAll(userId);
        if (meals == null) return null;

        if (meals.size() == 0)
            throw new NotFoundException("Meals for specified user is not found");
        else
            return new ArrayList<>(meals);
    }

    public void update(int userId, Meal meal) throws NotFoundException {
        checkUserExists(repository.userExists(userId), userId);
        checkNotFoundWithId(repository.save(userId, meal), meal.getId());
    }

    public List<Meal> getAllByDate(String startDate, String endDate) {
        return repository.getAllByDate(parse(startDate), parse(endDate));
    }

    public List<Meal> getAllByTime(String startTime, String endTime) {
        return repository.getAllByTime(LocalTime.parse(startTime), LocalTime.parse(endTime));
    }

    public List<Meal> getAllByDateTime(String startDate, String endDate, String startTime, String endTime) {
        return repository.getAllByDateTime(parse(startDate), parse(endDate), LocalTime.parse(startTime), LocalTime.parse(endTime));
    }
}