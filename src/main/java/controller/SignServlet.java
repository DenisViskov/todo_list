package controller;

import model.User;
import persistence.UserStorage;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * @author Денис Висков
 * @version 1.0
 * @since 27.09.2020
 */
@WebServlet("/sign")
public class SignServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        if (check(login, password)) {
            HttpSession session = req.getSession();
            session.setAttribute("user", findUser(login));
            req.getRequestDispatcher("/index.jsp").forward(req, resp);
        }
    }

    private User findUser(String login) {
        UserStorage store = (UserStorage) getServletContext().getAttribute("Hiber");
        List<User> users = store.getAllUser();
        return users.stream()
                .filter(user -> user.getLogin().equals(login))
                .findFirst()
                .get();
    }

    private boolean check(String login, String password) {
        UserStorage store = (UserStorage) getServletContext().getAttribute("Hiber");
        List<User> users = store.getAllUser();
        return users.stream()
                .anyMatch(user -> user.getLogin().equals(login)
                        && user.getPassword().equals(password));
    }
}
