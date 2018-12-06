package rjm.romek.awscourse.filter;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.security.Principal;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ReLoginFilterTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpSession session;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @Mock
    private Principal principal;

    private final ReLoginFilter reLoginFilter = new ReLoginFilter();

    @Test
    public void doFilterInvokesChainWhenUserPrincipalFound() throws Exception {
        when(request.getUserPrincipal()).thenReturn(principal);
        when(principal.getName()).thenReturn("pal");

        reLoginFilter.doFilter(request, response, filterChain);

        verify(filterChain, times(1)).doFilter(request, response);
    }

    @Test
    public void doFilterInvokesChainWhenNoUserPrincipalAndLoggedOutUri() throws Exception {
        when(request.getRequestURI()).thenReturn("/login");

        reLoginFilter.doFilter(request, response, filterChain);

        verify(filterChain, times(1)).doFilter(request, response);
    }

    @Test
    public void doFilterRedirectToLoginWhenNoUserPrincipalAndNotLoggedOutUri() throws Exception {
        when(request.getRequestURI()).thenReturn("/chapter/1000");
        when(request.getSession()).thenReturn(session);
        when(session.getId()).thenReturn("abcde");

        reLoginFilter.doFilter(request, response, filterChain);

        verify(response, times(1)).encodeRedirectURL(anyString());
        verify(filterChain, times(0)).doFilter(request, response);
    }

}