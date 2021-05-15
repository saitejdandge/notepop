package in.notepop.server.acl;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class RoleAndAuthoritiesMapping {
    private static RoleAndAuthoritiesMapping instance;
    private final HashMap<String, List<GrantedAuthority>> mapping;

    private RoleAndAuthoritiesMapping() {
        mapping = new HashMap<>();

        mapping.put(Roles.ROLE_USER, List
                .of(Authorities.READ_USER_PROFILE)
                .stream().map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList()));

        mapping.put(Roles.ROLE_ADMIN, List
                .of(Authorities.READ_USER_PROFILE, Authorities.DELETE_USER)
                .stream().map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList()));
    }

    public static RoleAndAuthoritiesMapping getInstance() {
        if (instance == null)
            instance = new RoleAndAuthoritiesMapping();
        return instance;
    }

    public List<GrantedAuthority> getAuthoritiesOfRole(String role) {
        return mapping.get(role);
    }

    public List<GrantedAuthority> getAuthoritiesOfRole(List<String> roles) {
        List<GrantedAuthority> result = new ArrayList<>();
        if (roles != null)
            for (String role : roles)
                result.addAll(mapping.get(role));
        return result;
    }
}
