package controller.servlet;

import controller.answer.Answer;
import controller.answer.IndexAnswerGenerator;
import model.Category;
import model.Task;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.CategoryStore;
import persistence.HbStore;
import persistence.TaskStore;
import persistence.UserStorage;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * Class is an IndexServlet
 *
 * @author Денис Висков
 * @version 1.0
 * @since 20.09.2020
 */
@WebServlet(name = "index", urlPatterns = "/index", loadOnStartup = 1)
public class IndexServlet extends HttpServlet {

    /**
     * Storage
     */
    private TaskStore taskStore;
    /**
     * Logger
     */
    private static final Logger LOG = LoggerFactory.getLogger(IndexServlet.class);


    @Override
    public void init() throws ServletException {
        taskStore = HbStore.instOf();
        getServletContext().setAttribute("Hiber", taskStore);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        PrintWriter writer = resp.getWriter();
        Answer answer = new IndexAnswerGenerator(taskStore, req.getParameter("request"));
        writer.print(answer.toFormAnswer());
        writer.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        if (req.getParameter("selected") != null) {
            String[] parameterValues = req.getParameterValues("name[]");
            updateTasks(parameterValues);
        } else {
            String name = req.getParameter("name");
            String description = req.getParameter("description");
            String[] categoryID = req.getParameterValues("id[]");
            saveUserWithTask(name, description, categoryID, req.getSession());
        }
    }

    /**
     * Method of save user with task
     *
     * @param name
     * @param description
     * @param categoryID
     * @param session
     */
    private void saveUserWithTask(String name, String description, String[] categoryID, HttpSession session) {
        Task task = new Task(0, name, description, Timestamp.valueOf(LocalDateTime.now()), false);
        CategoryStore categoryStore = (CategoryStore) taskStore;
        for (String id : categoryID) {
            task.addCategory((Category) categoryStore.getCategory(Integer.valueOf(id)));
        }
        task = (Task) taskStore.add(task);
        User user = (User) session.getAttribute("user");
        user.setTask(task);
        UserStorage userStore = (UserStorage) taskStore;
        userStore.updateUser(user);
    }

    /**
     * Method execute updates in DB by given name
     *
     * @param names
     */
    private void updateTasks(String[] names) {
        List<Task> all = taskStore.getAll();
        for (String name : names) {
            all.forEach(task -> {
                if (task.getName().equals(name)) {
                    Task result = new Task(task.getId(),
                            task.getName(),
                            task.getDescription(),
                            task.getCreated(),
                            true);
                    result.setCategories(task.getCategories());
                    taskStore.update(result);
                }
            });
        }
    }

    @Override
    public void destroy() {
        try {
            taskStore.close();
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            e.printStackTrace();
        }
    }
}
