package ru.javawebinar.topjava.service;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.Month;
import java.util.*;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    private static Map<String, Long> testsResults;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Rule
    public final TestRule watcher = new TestWatcher() {

        private long startTime;

        @Override
        public void starting(Description description) {
            startTime = System.currentTimeMillis();
        }

        @Override
        public void finished(Description description) {
            String testName = description.getMethodName();
            testsResults.put(testName, System.currentTimeMillis() - startTime);
            System.out.println("\n================================\n" +
                               testName + " has finished. Execution time: " + testsResults.get(testName) + " ms" +
                              "\n================================\n");
        }
    };

    @BeforeClass
    public static void mapInitialization(){
        testsResults = new LinkedHashMap<>();
    }

    @AfterClass
    public static void printResults(){
        System.out.println("\n========== TESTS RESULTS ==========\n");

        for (Map.Entry<String, Long> test: testsResults.entrySet())
            System.out.println(test.getKey() + " - " + test.getValue() + " ms");

        System.out.println("\n========== TESTS RESULTS ==========\n");
        testsResults.clear();
    }

    @Autowired
    private MealService service;

    @Test
    public void delete() throws Exception {
        service.delete(MEAL1_ID, USER_ID);
        assertMatch(service.getAll(USER_ID), MEAL6, MEAL5, MEAL4, MEAL3, MEAL2);
    }

    @Test()
    public void deleteNotFound() throws Exception {
        exception.expect(NotFoundException.class);
        exception.expectMessage("Not found entity with id=" + 1);
        service.delete(1, USER_ID);
    }

    @Test
    public void deleteNotOwn() throws Exception {
        exception.expect(NotFoundException.class);
        exception.expectMessage("Not found entity with id=" + MEAL1_ID);
        service.delete(MEAL1_ID, ADMIN_ID);
    }

    @Test
    public void create() throws Exception {
        Meal newMeal = getCreated();
        Meal created = service.create(newMeal, USER_ID);
        newMeal.setId(created.getId());
        assertMatch(newMeal, created);
        assertMatch(service.getAll(USER_ID), newMeal, MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1);
    }

    @Test
    public void get() throws Exception {
        Meal actual = service.get(ADMIN_MEAL_ID, ADMIN_ID);
        assertMatch(actual, ADMIN_MEAL1);
    }

    @Test
    public void getNotFound() throws Exception {
        exception.expect(NotFoundException.class);
        exception.expectMessage("Not found entity with id=" + 1);
        service.get(1, USER_ID);
    }

    @Test
    public void getNotOwn() throws Exception {
        exception.expect(NotFoundException.class);
        exception.expectMessage("Not found entity with id=" + MEAL1_ID);
        service.get(MEAL1_ID, ADMIN_ID);
    }

    @Test
    public void update() throws Exception {
        Meal updated = getUpdated();
        service.update(updated, USER_ID);
        assertMatch(service.get(MEAL1_ID, USER_ID), updated);
    }

    @Test
    public void updateNotFound() throws Exception {
        exception.expect(NotFoundException.class);
        exception.expectMessage("Not found entity with id=" + MEAL1.getId());
        service.update(MEAL1, ADMIN_ID);
    }

    @Test
    public void getAll() throws Exception {
        assertMatch(service.getAll(USER_ID), MEALS);
    }

    @Test
    public void getBetween() throws Exception {
        assertMatch(service.getBetweenDates(
                LocalDate.of(2015, Month.MAY, 30),
                LocalDate.of(2015, Month.MAY, 30), USER_ID), MEAL3, MEAL2, MEAL1);
    }
}