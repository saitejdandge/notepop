package in.notepop.server.config;

import in.notepop.server.acl.Roles;
import in.notepop.server.constants.SecurityConstants;
import in.notepop.server.filters.JWTAuthenticationFilter;
import in.notepop.server.filters.JWTAuthorizationFilter;
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

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    /*Use different services based on the actor*/
    @Autowired
    UserService userDetailsService;

    @Autowired
    SessionService sessionService;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //set your config on the auth object
//        auth.inMemoryAuthentication()
//                .withUser("blah")
//                .password("blah")
//                .roles("USER")
//                .and()
//                .withUser("admin")
//                .password("admin")
//                .roles("ADMIN");
        auth.userDetailsService(userDetailsService);
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        //dealing with clear text
        return NoOpPasswordEncoder.getInstance();
    }

    /*Authorization*/
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/admin").hasRole(Roles.ROLE_ADMIN)
                .antMatchers("/user").hasAnyRole(Roles.ROLE_ADMIN, Roles.ROLE_USER)
                .antMatchers(SecurityConstants.LOGIN_URL).permitAll()
                .anyRequest().authenticated()
                .and()
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
