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

    public List<Meal> getAll(){
       log.info("getAll ");
       return service.getAll(SecurityUtil.authUserId());
    }

    public List<MealTo> getTo(List<Meal> meals){
        log.info("get TO");
        return MealsUtil.getTos(meals, MealsUtil.DEFAULT_CALORIES_PER_DAY);
    }

    public List<Meal> getAllFiltered(LocalDate startDate, LocalDate endDate,
                                     LocalTime startTime, LocalTime endTime) {

        log.info("getAll filtered by date and time");
        return service.getAllByDateTime(SecurityUtil.authUserId(),
                                        startDate, endDate, startTime, endTime);
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