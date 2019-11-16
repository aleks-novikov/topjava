package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.http.HttpServletRequest;
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
       return service.getAll();
    }

    public List<MealTo> getTo(List<Meal> meals){
        log.info("get TO");
        return MealsUtil.getTos(meals, MealsUtil.DEFAULT_CALORIES_PER_DAY);
    }

    public List<Meal> getAllFiltered(HttpServletRequest request) {

        if (filterByDate(request) && filterByTime(request)) {
            log.info("getAll filtered by date and time");
            return service.getAllByDateTime(request.getParameter("startDate"), request.getParameter("endDate"),
                    request.getParameter("startTime"), request.getParameter("endTime"));
        }

        if (filterByDate(request)) {
            log.info("getAll filtered by date");
            return service.getAllByDate(request.getParameter("startDate"),
                                        request.getParameter("endDate"));
        }

        log.info("getAll filtered by time");
        return service.getAllByTime(request.getParameter("startTime"),
                                    request.getParameter("endTime"));
    }

    public Meal get(int id) {
        log.info("get {}", id);
        return service.get(id);
    }

    public Meal create(Meal meal) {
        log.info("create {}", meal);
        checkIsNew(meal);
        return service.create(meal);
    }

    public void delete(int id) {
        log.info("delete {}", id);
        service.delete(id);
    }

    public void update(Meal meal, int id) {
        log.info("update {} with id={}", meal, id);
        assureIdConsistent(meal, id);
        service.update(meal);
    }

    public boolean useFilterByDateTime(HttpServletRequest request) {
        return filterByDate(request) || filterByTime(request);
    }

    private boolean filterByDate(HttpServletRequest request) {
        return isNotEmpty(request.getParameter("startDate")) &&
               isNotEmpty(request.getParameter("endDate"));
    }

    private boolean filterByTime(HttpServletRequest request) {
        return isNotEmpty(request.getParameter("startTime")) &&
               isNotEmpty(request.getParameter("endTime"));
    }

    private boolean isNotEmpty(String param) {
        return param != null && !param.isEmpty();
    }
}