package controller.servlet;

import model.User;
import org.json.JSONObject;
import org.junit.Test;
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
import static org.mockito.Mockito.verify;

public class RegServletTest {

    @Test
    public void doGetTest() throws IOException, ServletException {
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        PrintWriter writer = mock(PrintWriter.class);
        when(resp.getWriter()).thenReturn(writer);
        doNothing().when(writer).print(any(JSONObject.class));
        new RegServlet().doGet(req, resp);
        verify(writer).flush();
    }

    @Test
    public void doPostTest() throws ServletException, IOException {
        UserStorage storage = mock(UserStorage.class);
        RegServlet regServlet = new RegServlet();
        ServletConfig config = mock(ServletConfig.class);
        ServletContext context = mock(ServletContext.class);
        when(config.getServletContext()).thenReturn(context);
        when(context.getAttribute("Hiber")).thenReturn(storage);
        regServlet.init(config);
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        when(req.getParameter("login")).thenReturn("login");
        when(req.getParameter("password")).thenReturn("pass");
        when(storage.getAllUser()).thenReturn(List.of(new User(0, "asd", "pass", null)));
        regServlet.doPost(req, resp);
        verify(storage).addUser(any());
    }
}