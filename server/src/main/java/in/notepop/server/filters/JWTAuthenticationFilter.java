package in.notepop.server.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import in.notepop.server.config.LoggedInUser;
import in.notepop.server.constants.SecurityConstants;
import in.notepop.server.exceptions.BaseException;
import in.notepop.server.exceptions.ErrorCodes;
import in.notepop.server.requests.AuthRequest;
import in.notepop.server.responses.ResponseWrapper;
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
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authManager;
    private final SessionService sessionService;


    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, SessionService sessionService) {
        this.authManager = authenticationManager;
        this.sessionService = sessionService;
        setFilterProcessesUrl(SecurityConstants.LOGIN_URL);
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response)
            throws AuthenticationException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        AuthRequest authRequest = null;
        try {
            authRequest = mapper.readValue(request.getInputStream(), AuthRequest.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getUsername(),
                        authRequest.getPassword() != null ? authRequest.getPassword() : authRequest.getUsername(),
                        new ArrayList<>())
        );
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

    /*Will be called if any of the auth providers throw AuthenticationException*/
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response,
                                              AuthenticationException failed) {
        throw new BaseException(ErrorCodes.STANDARD_ERROR);
    }

    private Session updateOrCreateSession(User principal, String token) {
        Session session;
        session = sessionService.findByUserId(principal.getUsername());
        Session newSession = new Session.SessionBuilder()
                .accessToken(token)
                //10 hour expiry
                .refreshToken(token)
                .role(principal.getRolesWithComma())
                .userType(principal.getUserType())
                .expiry(ZonedDateTime.now(ZoneId.of(SecurityConstants.TIMEZONE)).plusMinutes(SecurityConstants.EXPIRATION_IN_MINUTES))
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
                .withExpiresAt(new Date(System.currentTimeMillis() + (SecurityConstants.EXPIRATION_IN_MINUTES * 60 * 1000)))
                .sign(Algorithm.HMAC512(SecurityConstants.SECRET.getBytes()));
    }
}
