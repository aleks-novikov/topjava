package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({"classpath:spring/spring-app.xml",
                       "classpath:spring/spring-db.xml"})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    private static final int NOT_EXISTING_MEAL_ID = 1;

    @Autowired
    MealService service;

    static {
        SLF4JBridgeHandler.install();
    }

    @Test
    public void get() {
        Meal expected = service.get(USER_MEAL_ID_1, USER_ID);
        assertThat(USER_MEAL_1).isEqualTo(expected);
    }

    @Test
    public void delete() {
        service.delete(USER_MEAL_ID_2, USER_ID);
        service.getAll(USER_ID);
        assertMatch(service.getAll(USER_ID), USER_MEAL_1, USER_MEAL_3);
    }

    @Test
    public void getBetweenDates() {
        List<Meal> actual = service.getBetweenDates(LocalDate.of(2019, 10, 1), LocalDate.of(2019, 10, 14), USER_ID);
        assertMatch(actual, USER_MEAL_1, USER_MEAL_3);
    }

    @Test
    public void getAll() {
        assertMatch(service.getAll(ADMIN_ID), ADMIN_MEAL_1, ADMIN_MEAL_2);
    }

    @Test
    public void update() {
        Meal updatedMeal = new Meal(ADMIN_MEAL_1);
        updatedMeal.setCalories(1800);
        updatedMeal.setDescription("Ужин");
        service.update(updatedMeal, ADMIN_ID);
        assertMatch(service.get(ADMIN_MEAL_ID_1, ADMIN_ID), updatedMeal);
    }

    @Test
    public void create() {
        Meal newMeal = new Meal(LocalDateTime.now(), "Новая еда", 500);
        Meal createdMeal = service.create(newMeal, ADMIN_ID);
        newMeal.setId(createdMeal.getId());
        assertMatch(service.getAll(ADMIN_ID), newMeal, ADMIN_MEAL_1, ADMIN_MEAL_2);
    }

    @Test(expected = NotFoundException.class)
    public void getForeign() {
        service.get(USER_MEAL_ID_1, ADMIN_ID);
    }

    @Test(expected = NotFoundException.class)
    public void updateForeign() {
        Meal foreignMeal = new Meal(ADMIN_MEAL_1);
        foreignMeal.setId(ADMIN_MEAL_ID_1);
        service.update(foreignMeal, USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void deleteForeign() {
        service.delete(ADMIN_MEAL_ID_1, USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void getNotExisting() {
        service.get(NOT_EXISTING_MEAL_ID, USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void deleteNotExisting() {
        service.delete(NOT_EXISTING_MEAL_ID, USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void updateNotExisting() {
        Meal meal = new Meal(ADMIN_MEAL_1);
        meal.setId(NOT_EXISTING_MEAL_ID);
        service.update(meal, USER_ID);
    }
}