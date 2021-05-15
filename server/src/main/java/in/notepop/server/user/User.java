package in.notepop.server.user;

import in.notepop.server.acl.Roles;
import in.notepop.server.shared.BaseActor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Collection;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Entity
@Table(name = "app_user")
public class User extends BaseActor implements UserDetails {

    @Id
    private String username;
    private String userType;

    private User(String uniqueId, String userType, String... roles) {
        super(roles);
        this.userType = userType;
        this.username = uniqueId;
        this.active = true;
    }

    public static User getNormalUser(String username) {
        return new User(username, User.UserType.NORMAL_USER, Roles.ROLE_USER);
    }

    public static User getAdminUser(String username) {
        return new User(username, User.UserType.ADMIN, Roles.ROLE_ADMIN, Roles.ROLE_USER);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.getGrantedAuthorities();
    }

    @Override
    public String getPassword() {
        return this.username;
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.isActive();
    }


    public class UserType {
        private UserType() {

        }

        public static final String NORMAL_USER = "NORMAL_USER";
        public static final String ADMIN = "ADMIN";
        public static final String MERCHANT = "MERCHANT";
    }
}
