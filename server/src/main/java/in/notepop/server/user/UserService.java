package in.notepop.server.user;

import in.notepop.server.config.AuthRequest;
import in.notepop.server.config.JwtUtil;
import in.notepop.server.config.MyUserDetails;
import in.notepop.server.session.Session;
import in.notepop.server.session.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final SessionService sessionService;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;


    @Autowired
    public UserService(UserRepository userRepository, SessionService sessionService, JwtUtil jwtUtil, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.sessionService = sessionService;
    }

    public Session login(AuthRequest authRequest) throws Exception {
        Session session = new Session();
        generateAndAttachToken(session, authRequest);
        return sessionService.createSession(session);
    }

    private void generateAndAttachToken(Session session, AuthRequest authRequest) throws Exception {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );
        MyUserDetails myUserDetails = (MyUserDetails) authentication.getPrincipal();
        //one hour expiry
        session.setAccessToken(jwtUtil.generateToken(myUserDetails, 1000 * 60 * 60));
        //10 hour expiry
        session.setRefreshToken(jwtUtil.generateToken(myUserDetails, 1000 * 60 * 60 * 10));
        session.setExpiry(new Timestamp(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10).getTime()));
    }
}
