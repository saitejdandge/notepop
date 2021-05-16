package in.notepop.server.auth_providers;

import in.notepop.server.user.User;
import in.notepop.server.user.UserService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.ArrayList;

public class UserAuthProvider implements AuthenticationProvider {
    private final UserService userService;

    public UserAuthProvider(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) {
        String username = authentication.getName();
        User userObject = userService.createUser(username);
        //todo check the authoritiees
        return new UsernamePasswordAuthenticationToken(userObject, userObject.getUsername(), new ArrayList<>());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }


}
