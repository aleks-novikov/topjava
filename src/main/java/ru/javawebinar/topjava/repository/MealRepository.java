package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;

public interface MealRepository {
    // null if not found, when updated
    Meal save(Meal meal);

    // false if not found
    boolean delete(int id);

    // null if not found
    Meal get(int id);

    Collection<Meal> getAll();

    List<Meal> getAllByDate(LocalDate startDate, LocalDate endDate);

    List<Meal> getAllByTime(LocalTime startTime, LocalTime endTime);

    List<Meal> getAllByDateTime(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime);
}
