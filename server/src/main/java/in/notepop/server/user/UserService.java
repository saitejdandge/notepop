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

    public AppUser login(String uniqueId) {
        Optional<AppUser> appUser = userRepository.findByUniqueId(uniqueId);
        if (appUser.isEmpty()) {
            AppUser appUser1 = new AppUser(uniqueId);
            userRepository.save(appUser1);
            return appUser1;
        } else {
            return appUser.get();
        }
    }
}
