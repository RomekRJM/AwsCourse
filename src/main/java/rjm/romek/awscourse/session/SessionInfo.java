package rjm.romek.awscourse.session;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import rjm.romek.awscourse.model.User;
import rjm.romek.awscourse.model.UserPrincipal;

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SessionInfo {

    private User user;

    public User getCurrentUser() {
        if (user == null) {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (UserPrincipal.ANONYMOUS_USERNAME == principal) {
                user = UserPrincipal.ANONYMOUS_USER;
            } else {
                user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            }
        }
        return user;
    }

}