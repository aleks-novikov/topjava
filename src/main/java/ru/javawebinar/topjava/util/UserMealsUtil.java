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
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 200),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Ужин", 500)
        );

        System.out.println("\nWithoutSorting");
        List<UserMealWithExceed> userMealWithExceed = getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 1750);
        userMealWithExceed.forEach(System.out::println);

        System.out.println("\nSortedAndThroughOnePass");
        userMealWithExceed = getFilteredWithExceededThroughOnePass(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 1750);
        userMealWithExceed.forEach(System.out::println);

        System.out.println("\nByStreams");
        userMealWithExceed = getFilteredWithExceededByStreams(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 1750);
        userMealWithExceed.forEach(System.out::println);
    }

    private static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime,
                                                                   LocalTime endTime, int caloriesLimit) {
        //подсчёт суммы калорий за каждый день
        Map<LocalDate, Integer> caloriesMap = new HashMap<>();
        mealList.forEach(meal ->
            caloriesMap.merge(meal.getDateTime().toLocalDate(), meal.getCalories(),
                    (dayCalories, mealCalories) -> dayCalories + mealCalories)
        );

        //заполнение mealsWithExceed
        List<UserMealWithExceed> result = new ArrayList<>();

        mealList.forEach(meal -> {
            boolean caloriesIsExceed = caloriesMap.get(meal.getDateTime().toLocalDate()) > caloriesLimit;

            UserMealWithExceed mealWithExceed = new UserMealWithExceed(meal.getDateTime(), meal.getDescription(),
                    meal.getCalories(), caloriesIsExceed);

            //фильтрация по дате
            if (TimeUtil.isBetween(mealWithExceed.getDateTime().toLocalTime(), startTime, endTime))
                result.add(mealWithExceed);
        });

        return result;
    }

    private static List<UserMealWithExceed> getFilteredWithExceededThroughOnePass(List<UserMeal> mealList, LocalTime startTime,
                                                                                  LocalTime endTime, int caloriesLimit) {
        UserMeal meal;
        List<UserMealWithExceed> result = new ArrayList<>();
        List<UserMeal> mealsPerCurrentDay = new ArrayList<>();
        Map<LocalDate, Integer> caloriesMap = new HashMap<>();

        Collections.sort(mealList);

        for (int i = 0; i < mealList.size(); i++) {
            meal = mealList.get(i);
            mealsPerCurrentDay.add(meal);

            //подсчёт суммы калорий за каждый день
            caloriesMap.merge(meal.getDateTime().toLocalDate(), meal.getCalories(),
                    (dayCalories, mealCalories) -> dayCalories + mealCalories);

            //если дата следующей еды отличается от даты текущей ИЛИ это последняя запись
            //сравниваем сумму калорий за день с лимитом
            if (i + 1 == mealList.size() ||
                !meal.getDateTime().toLocalDate().equals(mealList.get(i + 1).getDateTime().toLocalDate())) {

                boolean caloriesIsExceed = caloriesMap.get(meal.getDateTime().toLocalDate()) > caloriesLimit;

                for (UserMeal dayMeal: mealsPerCurrentDay) {
                    UserMealWithExceed mealWithExceed = new UserMealWithExceed(dayMeal.getDateTime(), dayMeal.getDescription(),
                                                                               dayMeal.getCalories(), caloriesIsExceed);

                    if (TimeUtil.isBetween(mealWithExceed.getDateTime().toLocalTime(), startTime, endTime))
                        result.add(mealWithExceed);
                }
                mealsPerCurrentDay.clear();
            }
        }
        return result;
    }

    private static List<UserMealWithExceed> getFilteredWithExceededByStreams(List<UserMeal> mealList, LocalTime startTime,
                                                                            LocalTime endTime, int caloriesLimit) {
        Map<LocalDate, Integer> caloriesMap = mealList.stream().collect(
                                                                Collectors.groupingBy(meal -> meal.getDateTime().toLocalDate(),
                                                                Collectors.summingInt(UserMeal::getCalories)));

        return mealList.stream()
                       .filter(meal -> TimeUtil.isBetween(meal.getDateTime().toLocalTime(), startTime, endTime))
                       .map(meal -> new UserMealWithExceed(meal.getDateTime(), meal.getDescription(), meal.getCalories(),
                                                           caloriesMap.get(meal.getDateTime().toLocalDate()) > caloriesLimit))
                       .collect(Collectors.toList());
    }
}