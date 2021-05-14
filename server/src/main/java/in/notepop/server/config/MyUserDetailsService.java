package in.notepop.server.config;

import in.notepop.server.user.User;
import in.notepop.server.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
    public MyUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUniqueId(username);
        User userObj;
        if (user.isEmpty()) {
            userObj = new User(username);
            userRepository.save(userObj);
        } else {
            userObj = user.get();
        }
        return new MyUserDetails(userObj);
    }
}
