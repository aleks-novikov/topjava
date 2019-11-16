package ru.javawebinar.topjava.web.servlets;

import org.slf4j.Logger;
import org.springframework.context.ConfigurableApplicationContext;
import ru.javawebinar.topjava.SpringContextFactory;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.web.user.AdminRestController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static org.slf4j.LoggerFactory.getLogger;

public class UserServlet extends HttpServlet {
    private static final Logger log = getLogger(UserServlet.class);

    private AdminRestController userController;
    private ConfigurableApplicationContext appCtx;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        appCtx = SpringContextFactory.getContext();
        userController = appCtx.getBean(AdminRestController.class);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");

        Set<Role> roles = new HashSet<>();
        Arrays.stream(request.getParameterMap().get("userRoles")).forEach(roleName ->
                roles.add(Role.getRoleByName(roleName)));

        String id = request.getParameter("id");
        User user = new User(id.isEmpty() ? null : Integer.valueOf(id),
                request.getParameter("name"), request.getParameter("email"),
                request.getParameter("password"), Integer.parseInt(request.getParameter("calories")),
                true, roles);

        if (id.isEmpty()) {
            userController.create(user);
        } else {
            userController.update(user, Integer.parseInt(id));
        }

        response.sendRedirect("users");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        switch (action == null ? "all" : action) {
            case "delete":
                int id = getId(request);
                userController.delete(id);
                response.sendRedirect("users");
                break;
            case "create":
            case "update":
                final User user = "create".equals(action) ?
                        new User(null, null) :
                        userController.get(getId(request));
                request.setAttribute("user", user);
                request.setAttribute("availableRoles", Role.values());
                request.getRequestDispatcher("/userForm.jsp").forward(request, response);
                break;
            case "all":
            default:
                log.info("get all users");
                request.setAttribute("users", userController.getAll());
                request.getRequestDispatcher("/users.jsp").forward(request, response);
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }

    @Override
    public void destroy() {
        super.destroy();
        if (appCtx.isActive()) appCtx.close();
    }
}
