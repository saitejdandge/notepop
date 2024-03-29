package in.notepop.server.shared;

import in.notepop.server.acl.RoleAndAuthoritiesMapping;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.MappedSuperclass;
import java.util.Arrays;
import java.util.List;

@Data
@MappedSuperclass
public abstract class BaseActor {

    /*List of roles delimited by comma*/
    protected String roles;
    protected boolean active;

    protected BaseActor(CharSequence... roles) {
        if (roles != null)
            this.roles = String.join(",", roles);
    }

    public String getRolesWithComma() {

        if (roles != null)
            return String.join(",", roles);
        else
            return null;
    }

    public List<GrantedAuthority> getGrantedAuthorities() {
        return RoleAndAuthoritiesMapping
                .getInstance()
                .getAuthoritiesOfRole(getRoles());
    }

    public List<String> getRoles() {
        if (roles != null)
            return Arrays.asList(roles.split(","));
        else
            return null;
    }
}
