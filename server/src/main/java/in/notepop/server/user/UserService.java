package in.notepop.server.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User login(String uniqueId) {
        Optional<User> appUser = userRepository.findByUniqueId(uniqueId);
        if (appUser.isEmpty()) {
            User user1 = new User(uniqueId);
            userRepository.save(user1);
            return user1;
        } else {
            return appUser.get();
        }
    }
}
