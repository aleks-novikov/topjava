package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkIsNew;

@Controller
public class MealRestController {

    @Autowired
    private MealService service;

    private final Logger log = LoggerFactory.getLogger(getClass());

    public List<MealTo> getAll() {
        log.info("getAll");

        List<Meal> meals = service.getAll(SecurityUtil.authUserId());

        return MealsUtil.getTos(meals.size() > 1
                        ? MealsUtil.sortByDate(meals) : meals,
                        SecurityUtil.authUserCaloriesPerDay());
    }

    public List<MealTo> getFiltered(LocalDate startDate, LocalDate endDate,
                                    LocalTime startTime, LocalTime endTime) {

        log.info("getAll filtered by date and time");
        List<MealTo> filteredMeals = service.getAllByDateTime(SecurityUtil.authUserId(),
                                     startDate, endDate, startTime, endTime,
                                     SecurityUtil.authUserCaloriesPerDay());

        return filteredMeals.size() > 1
                ? MealsUtil.sortByDate(filteredMeals)
                : filteredMeals;
    }


    public Meal get(int id) {
        log.info("get {}", id);
        return service.get(SecurityUtil.authUserId(), id);
    }

    public Meal create(Meal meal) {
        log.info("create {}", meal);
        checkIsNew(meal);
        return service.create(SecurityUtil.authUserId(), meal);
    }

    public void delete(int id) {
        log.info("delete {}", id);
        service.delete(SecurityUtil.authUserId(), id);
    }

    public void update(Meal meal, int id) {
        log.info("update {} with id={}", meal, id);
        assureIdConsistent(meal, id);
        service.update(SecurityUtil.authUserId(), meal);
    }

    public boolean useFilterByDateTime(HttpServletRequest request) {
        return isNotEmpty(request.getParameter("startDate")) ||
               isNotEmpty(request.getParameter("endDate"))   ||
               isNotEmpty(request.getParameter("startTime")) ||
               isNotEmpty(request.getParameter("endTime"));
    }

    private boolean isNotEmpty(String param) {
        return param != null && !param.isEmpty();
    }
}