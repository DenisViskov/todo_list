package controller;

import model.Task;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.PrepareOnlyThisForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import persistence.HbStore;
import persistence.Store;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(PowerMockRunner.class)
@PrepareOnlyThisForTest(HbStore.class)
@PowerMockIgnore("org.jacoco.agent.rt.*")
public class IndexServletTest {

    private static final Store store = mock(Store.class);
    private final HttpServletRequest req = mock(HttpServletRequest.class);
    private final HttpServletResponse resp = mock(HttpServletResponse.class);
    private final PrintWriter writer = mock(PrintWriter.class);
    private static final IndexServlet servlet = new IndexServlet();

    @BeforeClass
    public static void setUp() throws Exception {
        mockStatic(HbStore.class);
        when(HbStore.instOf()).thenReturn(store);
        servlet.init();
    }

    @Test
    public void whenDoGetGETRequestTest() throws IOException, ServletException {
        when(resp.getWriter()).thenReturn(writer);
        when(req.getParameter("request")).thenReturn("GET request");
        when(store.getNotDone()).thenReturn(Collections.emptyList());
        servlet.doGet(req, resp);
        verify(writer).close();
    }

    @Test
    public void whenDoGetGETAllRequestTest() throws IOException, ServletException {
        when(resp.getWriter()).thenReturn(writer);
        when(req.getParameter("request")).thenReturn("GET All");
        when(store.getAll()).thenReturn(Collections.emptyList());
        servlet.doGet(req, resp);
        verify(writer).close();
    }

    @Test
    public void whenSelectedDoPostTest() throws ServletException, IOException {
        when(req.getParameter("selected")).thenReturn("Any");
        when(req.getParameterValues("name[]")).thenReturn(new String[]{"Any", "Many"});
        when(store.getAll()).thenReturn(List.of(new Task(0, "Any", null, null, false),
                new Task(0, "Many", null, null, false)));
        doNothing().when(store).update(any());
        servlet.doPost(req, resp);
        verify(store, times(2)).update(any());
    }

    @Test
    public void whenAddNewTaskDoPostTest() throws ServletException, IOException {
        when(req.getParameter("selected")).thenReturn(null);
        when(req.getParameter("name")).thenReturn("Name");
        when(req.getParameter("description")).thenReturn("description");
        when(store.add(any())).thenReturn(null);
        servlet.doPost(req, resp);
        verify(store).add(any());
    }
}