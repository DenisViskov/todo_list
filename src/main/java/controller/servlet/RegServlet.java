package controller.servlet;

import controller.answer.Answer;
import controller.answer.RegAnswerGenerator;
import model.User;
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

    private final Answer answer = new RegAnswerGenerator();
    private UserStorage store;

    @Override
    public void init() throws ServletException {
        store = (UserStorage) getServletContext().getAttribute("Hiber");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        PrintWriter writer = resp.getWriter();
        writer.print(answer.toFormAnswer());
        writer.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String login = req.getParameter("login");
        String pass = req.getParameter("password");
        if (!checkUser(login)) {
            store.addUser(new User(0, login, pass, null));
            answer.setLastOperation(true);
        } else {
            answer.setLastOperation(false);
        }
    }

    /**
     * Method check of exist User in DB
     *
     * @param login
     * @return boolean
     */
    private boolean checkUser(String login) {
        List<User> list = store.getAllUser();
        return list.stream()
                .anyMatch(user -> user.getLogin().equals(login));
    }
}
