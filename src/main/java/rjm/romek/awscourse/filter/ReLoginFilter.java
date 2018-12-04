package rjm.romek.awscourse.filter;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.stereotype.Component;

import com.google.common.collect.ImmutableList;

@Component
@Order(1)
public class ReLoginFilter implements Filter {

    private final String LOGIN_URI = "/login";
    private final List<String> LOGGED_OUT_URIS = ImmutableList.of(LOGIN_URI, "/webjars", "/favicon.ico");
    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    Logger logger = LoggerFactory.getLogger(ReLoginFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;

        if (req.getUserPrincipal() == null || req.getUserPrincipal().getName() == null) {
            boolean allowed = false;
            for (String loggedOutUri : LOGGED_OUT_URIS) {
                if (req.getRequestURI().startsWith(loggedOutUri)) {
                    allowed = true;
                    break;
                }
            }

            if (!allowed) {
                logger.info("User tried to login using invalid session: " + req.getSession().getId() + " and was redirected to login page.");
                redirectStrategy.sendRedirect(req, (HttpServletResponse) response, LOGIN_URI);
                return;
            }
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
