package in.notepop.server.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "app_user")
public class User {
    @Id
    private String uniqueId;
    private String roles;
    private boolean active;

    public User(String uniqueId) {
        this.uniqueId = uniqueId;
        this.active = true;
        this.roles = "USER";
    }
}
