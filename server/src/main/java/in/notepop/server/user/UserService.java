package in.notepop.server.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public User createUser(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        User userObj;
        if (user.isEmpty()) {
            userObj = new User(username);
            userRepository.save(userObj);
        } else {
            userObj = user.get();
        }
        return userObj;
    }

    public boolean loginAdmin(String username, String password) throws UsernameNotFoundException {
        if (username.equals("admin") && password.equals("heeraj"))
            return true;
        throw new UsernameNotFoundException("Admin failed");
    }

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

}
