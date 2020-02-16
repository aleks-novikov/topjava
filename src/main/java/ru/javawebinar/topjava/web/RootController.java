package ru.javawebinar.topjava.web;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RootController {

    @GetMapping("/")
    public String root() {
        //возвращаем имя jsp-страницы, которую необходимо отобразить при запросе на указанный адрес
        return "redirect:meals";
    }

    // проверка происходит перед выполнением метода
    // методы идентичны, но @PreAuthorize может также принимать доп. аргументы
    // @Secured("ROLE_ADMIN")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
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
    public String getMeals() {
        return "meals";
    }
}
