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
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 750),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 20,10,0), "Обед", 300),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 10,10,0), "Ужин", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 15,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 10,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 5,20,0), "Ужин", 510)
        );

        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 500);

        getFilteredWithExceededByStreams(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
    }

    private static Set<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime,
                                                                   LocalTime endTime, int caloriesLimit) {
        UserMeal meal;
        Set<UserMealWithExceed> result = new HashSet<>();
        List<UserMeal> mealsPerCurrentDay = new ArrayList<>();
        HashMap<LocalDate, Integer> caloriesMap = new HashMap<>();

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