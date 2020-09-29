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
import java.io.PrintWriter;
import java.util.List;

/**
 * @author Денис Висков
 * @version 1.0
 * @since 27.09.2020
 */
@WebServlet("/sign")
public class SignServlet extends HttpServlet {

    private final Answer answer = new SignAnswerGenerator();
    private UserStorage storage;

    @Override
    public void init() throws ServletException {
        storage = (UserStorage) getServletContext().getAttribute("Hiber");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        PrintWriter writer = resp.getWriter();
        writer.print(answer.toFormAnswer());
        writer.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        if (check(login, password)) {
            HttpSession session = req.getSession();
            session.setAttribute("user", findUser(login));
            answer.setLastOperation(true);
        } else {
            answer.setLastOperation(false);
        }
    }

    private User findUser(String login) {
        List<User> users = storage.getAllUser();
        return users.stream()
                .filter(user -> user.getLogin().equals(login))
                .findFirst()
                .get();
    }

    private boolean check(String login, String password) {
        List<User> users = storage.getAllUser();
        return users.stream()
                .anyMatch(user -> user.getLogin().equals(login)
                        && user.getPassword().equals(password));
    }
}
