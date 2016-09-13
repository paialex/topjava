package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.repository.InMemoryMealRepository;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.service.MealServiceInt;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.util.MealsUtil.getFilteredWithExceeded;

/**
 * Created by Alex on 10-Sep-16.
 */
public class MealServlet extends HttpServlet {

    private static final Logger LOG = LoggerFactory.getLogger(MealServlet.class);
    private static MealRepository repository = new InMemoryMealRepository();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");

        Meal meal = new Meal(id.isEmpty() ? null : Integer.valueOf(id), LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.valueOf(request.getParameter("calories")));
        LOG.info(meal.isNew() ? "Create {}" : "Update {}", meal);
        repository.save(meal);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if(action == null){
            LOG.info("getAll");
            request.setAttribute("mealList", MealsUtil.getWithExeeded(repository.getAll(),2000));
            request.getRequestDispatcher("/mealList.jsp").forward(request, response);
        }else if (action.equals("delete")){
            int id = getId(request);
            response.sendRedirect("meals");

        }else{
            final Meal meal = action.equals("create") ?
                    new Meal(LocalDateTime.now(), "", 1000) : repository.get(getId(request));
            request.setAttribute("meal", meal);
            request.getRequestDispatcher("mealEdit.jsp").forward(request, response);
        }




    }

    private int getId(HttpServletRequest request){
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.valueOf(paramId);
    }
}
