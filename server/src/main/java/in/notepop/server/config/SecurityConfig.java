package in.notepop.server.config;

import in.notepop.server.acl.Roles;
import in.notepop.server.auth_providers.AdminAuthProvider;
import in.notepop.server.auth_providers.UserAuthProvider;
import in.notepop.server.constants.SecurityConstants;
import in.notepop.server.filters.JWTAuthenticationFilter;
import in.notepop.server.filters.JWTAuthorizationFilter;
import in.notepop.server.filters.exception_filter.ExceptionFilter;
import in.notepop.server.session.SessionService;
import in.notepop.server.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutFilter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    /*Use different services based on the actor*/
    @Autowired
    UserService userDetailsService;

    @Autowired
    SessionService sessionService;

    @Autowired
    private ExceptionFilter exceptionFilter;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //set your config on the auth object
        auth.authenticationProvider(adminAuthProvider())
                .authenticationProvider(userAuthProvider());
    }


    @Bean
    public UserAuthProvider userAuthProvider() {
        return new UserAuthProvider(userDetailsService);
    }

    @Bean
    public AdminAuthProvider adminAuthProvider() {
        return new AdminAuthProvider(userDetailsService);
    }


    @Bean
    public PasswordEncoder getPasswordEncoder() {
        //dealing with clear text
        return NoOpPasswordEncoder.getInstance();
    }

    /*Authorization*/
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/admin").hasRole(Roles.ROLE_ADMIN)
                .antMatchers("/user").hasAnyRole(Roles.ROLE_ADMIN, Roles.ROLE_USER)
                .antMatchers(SecurityConstants.LOGIN_URL).permitAll()
                .antMatchers("/").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(exceptionFilter, LogoutFilter.class)
                .addFilter(new JWTAuthenticationFilter(authenticationManager(), sessionService))
                .addFilter(new JWTAuthorizationFilter(authenticationManager()))
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        //this disables session creation on Spring Security
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
