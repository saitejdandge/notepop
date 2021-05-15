package in.notepop.server.session;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SessionService {
    private final SessionRepository sessionRepo;

    @Autowired
    SessionService(SessionRepository sessionRepository) {
        this.sessionRepo = sessionRepository;
    }


    public Session findByUserId(String username) {
        Optional<Session> sessionOptional = sessionRepo.findByUserUsername(username);
        return sessionOptional.orElse(null);
    }

    public Session save(Session session) {
        return sessionRepo.save(session);
    }
}
