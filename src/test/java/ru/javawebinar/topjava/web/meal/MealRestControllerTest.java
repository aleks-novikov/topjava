package ru.javawebinar.topjava.web.meal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import ru.javawebinar.topjava.web.AbstractControllerTest;
import ru.javawebinar.topjava.web.json.JsonUtil;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.TestUtil.*;
import static ru.javawebinar.topjava.UserTestData.*;
import static ru.javawebinar.topjava.util.MealsUtil.createTo;

public class MealRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = MealRestController.REST_URL + '/';

    @Autowired
    private MealService mealService;

    @Test
    void get() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + MEAL1_ID))
               .andExpect(status().isOk())
               .andDo(print())
               .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
               .andExpect(result -> assertMatch(readFromJsonMvcResult(result, Meal.class), MEAL1));
    }

    @Test
    void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(REST_URL + MEAL1_ID))
               .andExpect(status().isNoContent())
               .andDo(print());

        assertThrows(NotFoundException.class, () -> mealService.get(MEAL1_ID, USER_ID));
    }

    @Test
    void getAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL))
               .andExpect(status().isOk())
               .andDo(print())
               .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
               .andExpect(contentJson(MealsUtil.getTos(MEALS, USER.getCaloriesPerDay())));
    }

    @Test
    void createWithLocation() throws Exception {
        Meal newMeal = MealTestData.getNew();

        ResultActions action =
                mockMvc.perform(MockMvcRequestBuilders.post(REST_URL)
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(JsonUtil.writeValue(newMeal)))   //записываем объект в тело запроса в формате JSON
                       .andDo(print())
                       .andExpect(status().isCreated());

        Meal created = readFromJson(action, Meal.class);
        Integer newId = created.getId();
        newMeal.setId(newId);

        MealTestData.assertMatch(created, newMeal);
        MealTestData.assertMatch(mealService.get(newId, USER_ID), newMeal);
    }

    @Test
    void update() throws Exception {
        Meal updatedMeal = MealTestData.getUpdated();

        mockMvc.perform(MockMvcRequestBuilders.put(REST_URL + MEAL1_ID)
               .contentType(MediaType.APPLICATION_JSON)
               .content(JsonUtil.writeValue(updatedMeal)))
               .andDo(print())
               .andExpect(status().isNoContent());

        MealTestData.assertMatch(mealService.get(updatedMeal.getId(), USER_ID), updatedMeal);
    }

    @Deprecated
    @Test
    void getBetween() throws Exception {
        String start = "2015-05-30T09:00";
        String end = "2015-05-30T11:00";

        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + "filter?startDateTime=" + start + "&endDateTime=" + end)
               .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andDo(print())
               .andExpect(contentJson(createTo(MEAL1, false)));
    }
}
