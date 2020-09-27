package controller;

import model.User;
import org.json.JSONObject;
import persistence.Store;
import persistence.UserStorage;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * @author Денис Висков
 * @version 1.0
 * @since 27.09.2020
 */
@WebServlet("/registration")
public class RegServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        JSONObject json = new JSONObject();
        String login = req.getParameter("login");
        String pass = req.getParameter("password");
        if (!checkUser(login)) {
            UserStorage storage = (UserStorage) getServletContext().getAttribute("Hiber");
            storage.addUser(new User(0, login, pass, null));
            json.put("user", "was added");
            writer.print(json);
            writer.flush();
        }
        json.put("user", "exist");
        writer.print(json);
        writer.flush();
    }

    /**
     * Method check of exist User in DB
     *
     * @param login
     * @return boolean
     */
    private boolean checkUser(String login) {
        UserStorage store = (UserStorage) getServletContext().getAttribute("Hiber");
        List<User> list = store.getAllUser();
        return list.stream()
                .anyMatch(user -> user.getLogin().equals(login));
    }
}
