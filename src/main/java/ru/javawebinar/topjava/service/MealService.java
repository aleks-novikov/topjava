package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.time.LocalDate.parse;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealService {

    private final MealRepository repository;

    @Autowired
    public MealService(MealRepository repository) {
        this.repository = repository;
    }

    public Meal create(Meal meal) {
        return repository.save(meal);
    }

    public void delete(int id) throws NotFoundException {
        checkNotFoundWithId(repository.delete(id), id);
    }

    public Meal get(int id) throws NotFoundException {
        return checkNotFoundWithId(repository.get(id), id);
    }

    public List<Meal> getAll() {
        Collection<Meal> meals = repository.getAll();
        if (SecurityUtil.authUserId() != 0 && meals.size() == 0)
            throw new NotFoundException("Meals for specified user is not found");
        else
            return new ArrayList<>(meals);
    }

    public void update(Meal meal) throws NotFoundException {
        checkNotFoundWithId(repository.save(meal), meal.getId());
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