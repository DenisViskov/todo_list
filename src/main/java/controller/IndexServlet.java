package controller;

import model.Task;
import org.json.JSONObject;
import org.json.JSONStringer;
import persistence.HbStore;
import persistence.Store;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

/**
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

    @Override
    public void init() throws ServletException {
        store = HbStore.instOf();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        PrintWriter writer = resp.getWriter();
        if (req.getParameter("request").equals("GET request")) {
            writer.print(getNotDoneJSON());
            writer.flush();
            writer.close();
        }
        if (req.getParameter("request").equals("GET All")) {
            writer.print(getAllJSON());
            writer.flush();
            writer.close();
        }
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
            Task task = new Task(0, name, description, Timestamp.valueOf(LocalDateTime.now()), false);
            store.add(task);
        }
    }

    /**
     * Method execute updates in DB by given name
     *
     * @param names
     */
    private void updateTasks(String[] names) {
        List<Task> all = store.findAll();
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
    private JSONObject getAllJSON() throws IOException {
        List<Task> tasks = store.findAll();
        JSONObject json = new JSONObject();
        for (int i = 0; i < tasks.size(); i++) {
            json.put(String.valueOf(i), tasks.get(i).getName());
        }
        return json;
    }

    /**
     * Method return ready json for send to client
     *
     * @return JSONObject
     * @throws IOException
     */
    private JSONObject getNotDoneJSON() throws IOException {
        List<Task> tasks = store.findAll();
        JSONObject json = new JSONObject();
        for (int i = 0; i < tasks.size(); i++) {
            if (!tasks.get(i).isDone()) {
                json.put(String.valueOf(i), tasks.get(i).getName());
            }
        }
        return json;
    }

    @Override
    public void destroy() {
        try {
            store.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
