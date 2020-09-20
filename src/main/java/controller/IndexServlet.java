package controller;

import model.Task;
import persistence.HbStore;
import persistence.Store;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * @author Денис Висков
 * @version 1.0
 * @since 20.09.2020
 */
@WebServlet("/index")
public class IndexServlet extends HttpServlet {

    /**
     * Storage
     */
    private Store store;

    @Override
    public void init() throws ServletException {
        store = new HbStore();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String name = req.getParameter("name");
        String description = req.getParameter("description");
        Task task = new Task(0, name, description, Timestamp.valueOf(LocalDateTime.now()), false);
        store.add(task);
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
