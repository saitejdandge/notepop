package in.notepop.server.auth_providers;

import in.notepop.server.user.User;
import in.notepop.server.user.UserService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import java.util.ArrayList;

public class AdminAuthProvider implements AuthenticationProvider {
    private final UserService userService;

    public AdminAuthProvider(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UsernamePasswordAuthenticationToken authToken = null;
        String username = authentication.getName();
        String credentials = (String) authentication.getCredentials();
        userService.loginAdmin(username, credentials);
        authToken = new UsernamePasswordAuthenticationToken(User.getAdminUser(username), null, new ArrayList<>());
        return authToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}
