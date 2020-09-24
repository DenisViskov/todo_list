package controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import persistence.HbStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

import static org.junit.Assert.*;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;

public class IndexServletTest {

    @Test
    public void doGetTest() throws IOException, ServletException {
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        PrintWriter writer = mock(PrintWriter.class);
        when(resp.getWriter()).thenReturn(writer);
        when(req.getParameter("request")).thenReturn("GET request");
        new IndexServlet().doGet(req, resp);
        verify(writer).print(anyChar());
    }

    @Test
    public void doPostTest() {
    }
}