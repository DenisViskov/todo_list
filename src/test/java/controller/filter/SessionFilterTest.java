package controller.filter;

import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import static org.junit.Assert.*;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;

public class SessionFilterTest {

    @Test
    public void whenWeHaveURLSignTest() throws IOException, ServletException {
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        FilterChain filterChain = mock(FilterChain.class);
        when(req.getRequestURI()).thenReturn("/sign.jsp");
        doNothing().when(filterChain).doFilter(req, resp);
        new SessionFilter().doFilter(req, resp, filterChain);
        verify(filterChain).doFilter(req, resp);
    }

    @Test
    public void whenWeHaveURLRegTest() throws IOException, ServletException {
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        FilterChain filterChain = mock(FilterChain.class);
        when(req.getRequestURI()).thenReturn("/registration.jsp");
        doNothing().when(filterChain).doFilter(req, resp);
        new SessionFilter().doFilter(req, resp, filterChain);
        verify(filterChain).doFilter(req, resp);
    }

    @Test
    public void whenShouldBeRedirectTest() throws IOException, ServletException {
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        FilterChain filterChain = mock(FilterChain.class);
        HttpSession session = mock(HttpSession.class);
        when(req.getRequestURI()).thenReturn("/");
        when(req.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(null);
        new SessionFilter().doFilter(req, resp, filterChain);
        verify(resp).sendRedirect(anyString());
    }


}