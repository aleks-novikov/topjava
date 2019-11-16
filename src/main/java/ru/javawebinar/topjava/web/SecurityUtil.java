package ru.javawebinar.topjava.web;

import static ru.javawebinar.topjava.util.MealsUtil.DEFAULT_CALORIES_PER_DAY;

public class SecurityUtil {

    private static Integer authUserId;

    public static int authUserId() {
        if (authUserId == null) {
            authUserId = 0;
        }
        return authUserId;
    }

    public static void setAuthUserId(int id) {
        authUserId = id;
    }

    public static int authUserCaloriesPerDay() {
        return DEFAULT_CALORIES_PER_DAY;
    }
}