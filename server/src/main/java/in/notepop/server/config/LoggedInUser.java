package in.notepop.server.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.Principal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoggedInUser implements Principal {
    private String username;
    private String role;

    @Override
    public String getName() {
        return username;
    }
}
