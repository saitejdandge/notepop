package in.notepop.server.config;

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
        try {
            boolean result = userService.loginAdmin(username, credentials);
            authToken = new UsernamePasswordAuthenticationToken(username, credentials, new ArrayList<>());
        } catch (Exception e) {
            return null;
        }
        return authToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}
