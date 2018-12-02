package rjm.romek.awscourse.session;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import rjm.romek.awscourse.model.User;
import rjm.romek.awscourse.model.UserPrincipal;

@Component
@Scope(scopeName = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SessionInfo {

    private User user;

    public User getCurrentUser() {
        if (user == null) {
            user = ((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        }
        return user;
    }

}