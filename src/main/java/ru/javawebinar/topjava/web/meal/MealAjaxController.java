package ru.javawebinar.topjava.web.meal;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/ajax/profile/meals")
public class MealAjaxController extends AbstractMealController {

    @Override
    @GetMapping("/{id}")
    public Meal get(@PathVariable int id) {
        return super.get(id);
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        super.delete(id);
    }

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MealTo> getAll() {
        return super.getAll();
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void createOrUpdate(@RequestParam Integer id,
                               @RequestParam String dateTime,
                               @RequestParam String description,
                               @RequestParam String calories) {

        Meal meal = new Meal(id, LocalDateTime.parse(dateTime), description, Integer.parseInt(calories));
        if (meal.isNew())
            super.create(meal);
    }

    @Override
    @GetMapping(value = "/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MealTo> getBetween(@RequestParam (value = "startDate", required = false) LocalDate startDate,
                                   @RequestParam (value = "startTime", required = false) LocalTime startTime,
                                   @RequestParam (value = "endDate", required = false) LocalDate endDate,
                                   @RequestParam (value = "endTime", required = false) LocalTime endTime) {
        return super.getBetween(startDate, startTime, endDate, endTime);
    }
}
