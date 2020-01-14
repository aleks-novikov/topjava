package ru.javawebinar.topjava.web.meal;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping(value = MealRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class MealRestController extends AbstractMealController {

    static final String REST_URL = "/rest/profile/meals";

    @Override
    @GetMapping("/{id}")
    // @PathVariable означает, что параметр берётся из запроса
    public Meal get(@PathVariable int id) {
        return super.get(id);
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)      // в ответе ничего не возвращаем, кроме статуса 204
    public void delete(@PathVariable int id) {
        super.delete(id);
    }

    @Override
    @GetMapping
    public List<MealTo> getAll() {
        return super.getAll();
    }


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)                 // принимаем объекты в теле запроса в формате JSON
    public ResponseEntity<Meal> createWithLocation(@RequestBody Meal meal) {  // @RequestBody означает, что Meal приходит в теле запроса
                                                                              // ResponseEntity<Meal> - в заголовке ответа отдаём URL на имя созданного ресурса Meal
        Meal created = super.create(meal);
        URI uriOfNewMeal = ServletUriComponentsBuilder.fromCurrentContextPath()
                            .path(REST_URL + "/{id}")
                            .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewMeal).body(created);
    }

    @Override
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@RequestBody Meal meal, @PathVariable int id) {
        super.update(meal, id);
    }

    @GetMapping(value = "/filter")
    // @RequestParam означает, что параметр передаётся в запросе
    // @Nullable означает, что параметр может быть null.
    // Если не задать параметр в тесте filterWithEmptyDates и не указать для него в методе @Nullable, тест упадёт
    public List<MealTo> getBetween(@RequestParam @Nullable LocalDate startDate, @RequestParam @Nullable LocalTime startTime,
                                   @RequestParam @Nullable LocalDate endDate, @RequestParam @Nullable LocalTime endTime) {
        return super.getBetween(startDate, startTime, endDate, endTime);
    }
}