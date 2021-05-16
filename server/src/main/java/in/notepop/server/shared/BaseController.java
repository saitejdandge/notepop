package in.notepop.server.shared;

import in.notepop.server.config.LoggedInUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;

public class BaseController {

    protected Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    protected LoggedInUser getPrincipal() {
        return (LoggedInUser) getAuthentication().getPrincipal();
    }

    protected String getLoggedInUserId() {
        return getAuthentication().getName();
    }

    protected String getPrincipalRole() {
        LoggedInUser principal = getPrincipal();
        return principal != null && principal.getRole() != null ? principal.getRole().get(0) : null;
    }

    protected Collection<? extends GrantedAuthority> getLoggedInUserAuthorities() {
        return getAuthentication().getAuthorities();
    }
}
