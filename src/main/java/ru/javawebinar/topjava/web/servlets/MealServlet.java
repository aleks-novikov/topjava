package ru.javawebinar.topjava.web.servlets;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class MealServlet extends HttpServlet {

    private MealRestController mealController;
    private ConfigurableApplicationContext appCtx;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml");
        mealController = appCtx.getBean(MealRestController.class);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");

        String id = request.getParameter("id");
        Meal meal = new Meal(id == null || id.isEmpty() ? null : getInt(id),
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                getInt(request.getParameter("calories")));

        if (meal.isNew())
            mealController.create(meal);
        else
            mealController.update(meal, getInt(id));

        response.sendRedirect("meals");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        switch (action == null ? "all" : action) {
            case "delete":
                int id = getId(request);
                mealController.delete(id);
                response.sendRedirect("meals");
                break;
            case "create":
            case "update":
                final Meal meal = "create".equals(action) ?
                        new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000) :
                        mealController.get(getId(request));
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("/mealForm.jsp").forward(request, response);
                break;
            case "all":
            default:

                String startDate = request.getParameter("startDate");
                String endDate = request.getParameter("endDate");
                String startTime = request.getParameter("startTime");
                String endTime = request.getParameter("endTime");

                List<MealTo> meals = !useFilterByDateTime(startDate, endDate, startTime, endDate)
                               ? mealController.getAll()
                               : mealController.getFiltered(isNotEmpty(startDate) ? LocalDate.parse(startDate) : null,
                                                            isNotEmpty(endDate)   ? LocalDate.parse(endDate)   : null,
                                                            isNotEmpty(startTime) ? LocalTime.parse(startTime) : null,
                                                            isNotEmpty(endTime)   ? LocalTime.parse(endTime)   : null);

                request.setAttribute("meals", meals);
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
        }
    }

    private boolean useFilterByDateTime(String ... params) {
        return Arrays.stream(params).anyMatch(this::isNotEmpty);
    }

    private boolean isNotEmpty(String param) {
        return param != null && !param.isEmpty();
    }


    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return getInt(paramId);
    }

    private int getInt(String id) {
        return Integer.parseInt(id);
    }

    @Override
    public void destroy() {
        super.destroy();
        appCtx.close();
    }
}
