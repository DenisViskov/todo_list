package controller.servlet;

import model.Task;
import model.User;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import persistence.HbStore;
import persistence.TaskStore;
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

import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@RunWith(PowerMockRunner.class)
@PrepareForTest(HbStore.class)
public class IndexServletTest {

    @Test
    public void doGetTest() throws IOException, ServletException {
        IndexServlet servlet = new IndexServlet();
        ServletConfig config = mock(ServletConfig.class);
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        PrintWriter writer = mock(PrintWriter.class);
        PowerMockito.mockStatic(HbStore.class);
        PowerMockito.when(HbStore.instOf()).thenReturn(mock(TaskStore.class));
        when(req.getParameter(anyString())).thenReturn("word");
        when(resp.getWriter()).thenReturn(writer);
        doNothing().when(writer).print(any(JSONObject.class));
        when(config.getServletContext()).thenReturn(mock(ServletContext.class));
        servlet.init(config);
        servlet.doGet(req, resp);
        verify(writer).flush();
    }

    @Test
    public void doPostUpdateTest() throws ServletException, IOException {
        TaskStore taskStore = mock(TaskStore.class);
        IndexServlet servlet = new IndexServlet();
        ServletConfig config = mock(ServletConfig.class);
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        PowerMockito.mockStatic(HbStore.class);
        PowerMockito.when(HbStore.instOf()).thenReturn(taskStore);
        when(req.getParameter("selected")).thenReturn("any");
        when(req.getParameterValues("name[]")).thenReturn(new String[]{"value"});
        when(config.getServletContext()).thenReturn(mock(ServletContext.class));
        when(taskStore.getAll()).thenReturn(List.of(new Task(0, "value", "desc", null, false)));
        servlet.init(config);
        servlet.doPost(req, resp);
        verify(taskStore).update(any());
    }

    @Test
    public void doPostAddUserTest() throws ServletException, IOException {
        HttpSession session = mock(HttpSession.class);
        TaskStore taskStore = mock(TaskStore.class, withSettings().extraInterfaces(UserStorage.class));
        IndexServlet servlet = new IndexServlet();
        ServletConfig config = mock(ServletConfig.class);
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        PowerMockito.mockStatic(HbStore.class);
        PowerMockito.when(HbStore.instOf()).thenReturn(taskStore);
        when(req.getSession()).thenReturn(session);
        when(req.getParameter("selected")).thenReturn(null);
        when(req.getParameter("name")).thenReturn("name");
        when(req.getParameter("desc")).thenReturn("desc");
        when(config.getServletContext()).thenReturn(mock(ServletContext.class));
        when(session.getAttribute("user")).thenReturn(new User());
        servlet.init(config);
        servlet.doPost(req, resp);
        verify(taskStore).add(any());
    }
}