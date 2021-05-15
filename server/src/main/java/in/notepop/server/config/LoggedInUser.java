package in.notepop.server.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.Principal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoggedInUser implements Principal {
    private String username;
    private List<String> role;

    @Override
    public String getName() {
        return username;
    }
}
