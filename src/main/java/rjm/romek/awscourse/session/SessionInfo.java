package rjm.romek.awscourse.session;

import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import rjm.romek.awscourse.model.User;

@Component
@Scope("session")
public class SessionInfo {

    private User user;

    public User getCurrentUser() {
        if (user == null) {
            user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();;
        }
        return user;
    }

}