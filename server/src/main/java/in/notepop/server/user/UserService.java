package in.notepop.server.user;

import in.notepop.server.session.Session;
import in.notepop.server.session.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final SessionService sessionService;

    @Autowired
    public UserService(UserRepository userRepository, SessionService sessionService) {
        this.userRepository = userRepository;
        this.sessionService = sessionService;
    }

    public Session login(String uniqueId) {
        Optional<User> appUser = userRepository.findByUniqueId(uniqueId);
        Session session = new Session();
        if (appUser.isEmpty()) {
            User user1 = new User(uniqueId);
            session.setUser(user1);
            userRepository.save(user1);
        } else {
            session.setUser(appUser.get());
        }
        return sessionService.createSession(session);
    }
}
