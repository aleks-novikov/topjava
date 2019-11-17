package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

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
        return repository.getAll(userId);
    }

    public void update(int userId, Meal meal) throws NotFoundException {
        checkUserExists(repository.userExists(userId), userId);
        checkNotFoundWithId(repository.save(userId, meal), meal.getId());
    }

    public List<MealTo> getAllByDateTime(int userId, LocalDate startDate, LocalDate endDate,
                                         LocalTime startTime, LocalTime endTime, int caloriesLimit) {
        return repository.getAllByDateTime(userId, startDate, endDate, startTime, endTime, caloriesLimit);
    }
}