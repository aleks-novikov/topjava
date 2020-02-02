package ru.javawebinar.topjava.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.MealsUtil;

@Controller
public class RootController {

    @Autowired
    private MealService mealService;

    @GetMapping("/")
    public String root() {
        //возвращаем имя jsp-страницы, которую необходимо отобразить при запросе на указанный адрес
        return "redirect:meals";
    }

    @GetMapping("/users")
    public String getUsers() {
        //действие аналогично методу root()
        return "users";
    }

    @GetMapping(value = "/login")
    public String login() {
        return "login";
    }

    @GetMapping("/meals")
    public String getMeals(Model model) {
        model.addAttribute("meals",
                MealsUtil.getTos(mealService.getAll(SecurityUtil.authUserId()), SecurityUtil.authUserCaloriesPerDay()));
        return "meals";
    }
}
