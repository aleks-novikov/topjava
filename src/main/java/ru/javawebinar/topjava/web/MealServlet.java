package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.storage.MealMemoryStorage;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {

    private static final Logger log = getLogger(MealServlet.class);

    private MealMemoryStorage mealStorage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        mealStorage = new MealMemoryStorage();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            displayMealsList(request, response);
            return;
        }

        switch (action) {
            case "add":
                log.debug("Creating new meal");
                request.getRequestDispatcher("/meal.jsp").forward(request, response);
                break;

            case "update":
                Integer mealId = Integer.valueOf(request.getParameter("id"));
                log.debug("Updating meal with id " + mealId);
                request.setAttribute("meal", mealStorage.get(mealId));
                request.getRequestDispatcher("/meal.jsp").forward(request, response);
                break;

            case "delete":
                mealId = Integer.valueOf(request.getParameter("id"));
                log.debug("Deleting meal with id " + mealId);
                mealStorage.delete(mealId);
                response.sendRedirect("meals");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        log.debug("Collecting inserted meal data");

        LocalDateTime date = LocalDateTime.parse(request.getParameter("date"));
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));
        Meal meal = new Meal(date, description, calories);

        String id = request.getParameter("id");
        if (id.isEmpty())
             mealStorage.add(meal);
        else mealStorage.update(Integer.valueOf(id), meal);

        displayMealsList(request, response);
    }

    private void displayMealsList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("Getting meals list");
        request.setAttribute("mealsList", new ArrayList<>(mealStorage.getAll().values()));

        log.debug("Displaying meals list");
        request.getRequestDispatcher("/meals.jsp").forward(request, response);
    }
}
