package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface MealRepository {
    // null if not found, when updated
    Meal save(int userId, Meal meal);

    // false if not found
    boolean delete(int userId, int id);

    // null if not found
    Meal get(int userId, int id);

    List<Meal> getAll(int userId);

    List<Meal> getAllByDate(LocalDate startDate, LocalDate endDate);

    List<Meal> getAllByTime(LocalTime startTime, LocalTime endTime);

    List<Meal> getAllByDateTime(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime);

    boolean userExists(int userId);
}
