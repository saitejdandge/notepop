package in.notepop.server.session;


import in.notepop.server.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;

@Service
public class SessionService {
    private final SessionRepository sessionRepo;

    @Autowired
    SessionService(SessionRepository sessionRepository) {
        this.sessionRepo = sessionRepository;
    }


    public Session createSession(String accessToken, String refreshToken, User principal) {
        return sessionRepo.save(getMappedSession(accessToken, refreshToken, principal));
    }

    private Session getMappedSession(String accessToken, String refreshToken, User principal) {
        Session session = new Session();
        //one hour expiry
        session.setAccessToken(accessToken);
        //10 hour expiry
        session.setRefreshToken(refreshToken);
        session.setUser(principal);
        session.setExpiry(new Timestamp(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10).getTime()));
        return session;
    }
}
