package ru.javawebinar.topjava.util;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );

        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);

        getFilteredWithExceededByStreams(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
    }

    private static Set<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime,
                                                                   LocalTime endTime, int caloriesLimit) {
        //подсчёт суммы калорий за каждый день
        HashMap<LocalDate, Integer> caloriesMap = new HashMap<>();
        for (UserMeal meal : mealList) {
            caloriesMap.merge(meal.getDateTime().toLocalDate(), meal.getCalories(),
                    (dayCalories, mealCalories) -> dayCalories + mealCalories);
        }

        //заполнение mealsWithExceed
        Set<UserMealWithExceed> result = new HashSet<>();
        for (UserMeal meal : mealList) {
            boolean caloriesIsExceed = caloriesMap.get(meal.getDateTime().toLocalDate()) > caloriesLimit;

            UserMealWithExceed mealWithExceed = new UserMealWithExceed(meal.getDateTime(), meal.getDescription(),
                                                                       meal.getCalories(), caloriesIsExceed);
            //фильтрация по дате
            if (TimeUtil.isBetween(mealWithExceed.getDateTime().toLocalTime(), startTime, endTime))
                result.add(mealWithExceed);
        }
        return result;
    }
    private static Set<UserMealWithExceed> getFilteredWithExceededByStreams(List<UserMeal> mealList, LocalTime startTime,
                                                                            LocalTime endTime, int caloriesLimit) {
        Map<LocalDate, Integer> caloriesMap = mealList.stream().collect(
                                                                Collectors.groupingBy(meal -> meal.getDateTime().toLocalDate(),
                                                                Collectors.summingInt(UserMeal::getCalories)));

        return mealList.stream()
                       .map(meal -> new UserMealWithExceed(meal.getDateTime(), meal.getDescription(), meal.getCalories(),
                                                           caloriesMap.get(meal.getDateTime().toLocalDate()) > caloriesLimit))
                       .filter(mealWithExceed -> TimeUtil.isBetween(mealWithExceed.getDateTime().toLocalTime(), startTime, endTime))
                       .collect(Collectors.toSet());
    }
}