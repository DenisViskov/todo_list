package controller;

import model.Task;
import model.User;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.HbStore;
import persistence.Store;
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
    private Store store;

    /**
     * Logger
     */
    private static final Logger LOG = LoggerFactory.getLogger(IndexServlet.class);

    @Override
    public void init() throws ServletException {
        store = HbStore.instOf();
        getServletContext().setAttribute("Hiber", store);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        PrintWriter writer = resp.getWriter();
        if (req.getParameter("request").equals("GET request")) {
            writer.print(getJSON(store.getNotDone()));
            writer.flush();
        }
        if (req.getParameter("request").equals("GET All")) {
            writer.print(getJSON(store.getAll()));
            writer.flush();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        if (req.getParameter("selected") != null) {
            String[] parameterValues = req.getParameterValues("name[]");
            updateTasks(parameterValues);
            req.setAttribute("request", "GET request");
            doGet(req, resp);
        } else {
            String name = req.getParameter("name");
            String description = req.getParameter("description");
            if (!saveUserWithTask(name, description, req.getSession())) {
                resp.sendRedirect(req.getContextPath() + "/sign.jsp");
            }
        }
    }

    private boolean saveUserWithTask(String name, String description, HttpSession session) {
        boolean result = false;
        Task task = new Task(0, name, description, Timestamp.valueOf(LocalDateTime.now()), false);
        task = (Task) store.add(task);
        User user = (User) session.getAttribute("user");
        if (user != null) {
            user.setTask(task);
            UserStorage userStore = (UserStorage) store;
            userStore.updateUser(user);
            result = true;
        }
        return result;
    }

    /**
     * Method execute updates in DB by given name
     *
     * @param names
     */
    private void updateTasks(String[] names) {
        List<Task> all = store.getAll();
        for (String name : names) {
            all.forEach(task -> {
                if (task.getName().equals(name)) {
                    store.update(new Task(task.getId(),
                            task.getName(),
                            task.getDescription(),
                            task.getCreated(),
                            true));
                }
            });
        }
    }

    /**
     * Method return ready json for send to client
     *
     * @return JSONObject
     * @throws IOException
     */
    private JSONObject getJSON(List<Task> tasks) throws IOException {
        UserStorage userStorage = (UserStorage) store;
        List<User> users = userStorage.getAllUser();
        JSONObject json = new JSONObject();
        tasks.forEach(task -> users.forEach(user -> {
            if (user.getTask().getId() == task.getId()) {
                json.put(user.getLogin(), task.getName());
            }
        }));
        return json;
    }

    @Override
    public void destroy() {
        try {
            store.close();
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            e.printStackTrace();
        }
    }
}
