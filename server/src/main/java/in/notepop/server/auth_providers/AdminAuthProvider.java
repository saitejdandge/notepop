package in.notepop.server.auth_providers;

import in.notepop.server.acl.RoleAndAuthoritiesMapping;
import in.notepop.server.acl.Roles;
import in.notepop.server.user.User;
import in.notepop.server.user.UserService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

public class AdminAuthProvider implements AuthenticationProvider {
    private final UserService userService;

    public AdminAuthProvider(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) {
        UsernamePasswordAuthenticationToken authToken = null;
        String username = authentication.getName();
        String credentials = (String) authentication.getCredentials();
        if (userService.loginAdmin(username, credentials))
            authToken = new UsernamePasswordAuthenticationToken(User.getAdminUser(username), null, RoleAndAuthoritiesMapping.getInstance().getAuthoritiesOfRole(Roles.ROLE_ADMIN));
        return authToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}
