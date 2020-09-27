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

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/sign.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html");
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        if (check(login, password)) {
            HttpSession session = req.getSession();
            session.setAttribute("user", findUser(login));
            req.getRequestDispatcher("/index.jsp").forward(req, resp);
        }
        PrintWriter writer = resp.getWriter();
        writer.print("<h2>Login or password not same</h2>");
        writer.flush();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        doGet(req, resp);
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
