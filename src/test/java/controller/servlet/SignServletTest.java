package controller.servlet;

import model.User;
import org.json.JSONObject;
import org.junit.Test;
import org.mockito.Mockito;
import persistence.UserStorage;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import static org.junit.Assert.*;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class SignServletTest {

    @Test
    public void doGetTest() throws IOException, ServletException {
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        PrintWriter writer = mock(PrintWriter.class);
        when(resp.getWriter()).thenReturn(writer);
        doNothing().when(writer).print(any(JSONObject.class));
        new SignServlet().doGet(req, resp);
        verify(writer).flush();
    }

    @Test
    public void doPostTest() throws ServletException, IOException {
        UserStorage storage = mock(UserStorage.class);
        SignServlet signServlet = new SignServlet();
        ServletConfig config = mock(ServletConfig.class);
        ServletContext context = mock(ServletContext.class);
        when(config.getServletContext()).thenReturn(context);
        when(context.getAttribute("Hiber")).thenReturn(storage);
        signServlet.init(config);
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        when(req.getParameter("login")).thenReturn("login");
        when(req.getParameter("password")).thenReturn("pass");
        when(req.getSession()).thenReturn(session);
        when(storage.getAllUser()).thenReturn(List.of(new User(0, "login", "pass", null)));
        signServlet.doPost(req, resp);
        verify(session).setAttribute(eq("user"), any());
    }
}