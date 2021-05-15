package in.notepop.server.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import in.notepop.server.ResponseWrapper;
import in.notepop.server.config.AuthRequest;
import in.notepop.server.config.LoggedInUser;
import in.notepop.server.constants.SecurityConstants;
import in.notepop.server.session.Session;
import in.notepop.server.session.SessionService;
import in.notepop.server.user.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import static in.notepop.server.constants.SecurityConstants.EXPIRATION_TIME;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authManager;
    private final SessionService sessionService;


    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, SessionService sessionService) {
        this.authManager = authenticationManager;
        this.sessionService = sessionService;
        setFilterProcessesUrl(SecurityConstants.LOGIN_URL);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            AuthRequest authRequest = mapper.readValue(request.getInputStream(), AuthRequest.class);
            return authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequest.getUsername(),
                            authRequest.getPassword() != null ? authRequest.getPassword() : authRequest.getUsername(),
                            new ArrayList<>())
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException {
        User principal = (User) auth.getPrincipal();
        String token = getToken(new LoggedInUser(principal.getUsername(), principal.getRoles()));
        Session session = updateOrCreateSession(principal, token);
        res.getWriter().write(new Gson().toJson(ResponseWrapper.success(session)));
        res.getWriter().flush();

    }

    private Session updateOrCreateSession(User principal, String token) {
        Session session;
        session = sessionService.findByUserId(principal.getUsername());
        //one hour expiry
        Session newSession = new Session.SessionBuilder()
                .accessToken(token)
                //10 hour expiry
                .refreshToken(token)
                .role(principal.getRolesWithComma())
                .userType(principal.getUserType())
                .expiry(new Timestamp(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10).getTime()))
                .user(principal)
                .build();

        /*Existing session record*/
        if (session != null) {
            newSession.setId(session.getId());
        }
        return sessionService.save(newSession);
    }

    private String getToken(LoggedInUser loggedInUser) {
        return JWT.create()
                .withSubject(new Gson().toJson(loggedInUser))
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(SecurityConstants.SECRET.getBytes()));
    }
    //todo handle failure authentication
}
