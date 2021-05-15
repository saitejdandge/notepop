package in.notepop.server.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.google.gson.Gson;
import in.notepop.server.ResponseWrapper;
import in.notepop.server.acl.RoleAndAuthoritiesMapping;
import in.notepop.server.config.AuthResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static in.notepop.server.constants.SecurityConstants.*;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {


    public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String header = request.getHeader(HEADER_STRING);
        try {
            if (header == null || !header.startsWith(TOKEN_PREFIX)) {
                filterChain.doFilter(request, response);
                return;
            }
            SecurityContextHolder.getContext().setAuthentication(getAuthentication(request));
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            response.getWriter().write(new Gson().toJson(getErrorResponse(e)));
            response.getWriter().flush();
        }
    }

    private ResponseWrapper<Object> getErrorResponse(Exception e) {
        if (e instanceof TokenExpiredException) {
            return ResponseWrapper.error("Token expired", 401);
        } else if (e instanceof JWTDecodeException) {
            return ResponseWrapper.error("Invalid Token", 401);
        } else {
            return ResponseWrapper.error("Something went wrong", 401);
        }
    }

    // Reads the JWT from the Authorization header, and then uses JWT to validate the token
    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);

        if (token != null) {
            // parse the token.
            String parsedJwt = JWT.require(Algorithm.HMAC512(SECRET.getBytes()))
                    .build()
                    .verify(token.replace(TOKEN_PREFIX, ""))
                    .getSubject();
            AuthResponse authResponse = new Gson().fromJson(parsedJwt, AuthResponse.class);

            if (parsedJwt != null) {
                // new arraylist means authorities
                return new UsernamePasswordAuthenticationToken(authResponse, null, RoleAndAuthoritiesMapping.getInstance().getAuthoritiesOfRole(authResponse.getRole()));
            }
            return null;
        }

        return null;
    }
}
