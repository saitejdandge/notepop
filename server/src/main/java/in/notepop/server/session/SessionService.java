package in.notepop.server.session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SessionService {
    private final SessionRepository sessionRepo;

    @Autowired
    SessionService(SessionRepository sessionRepository) {
        this.sessionRepo = sessionRepository;
    }
}
