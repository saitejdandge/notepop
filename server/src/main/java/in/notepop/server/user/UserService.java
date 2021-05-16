package in.notepop.server.user;

import in.notepop.server.constants.SecurityConstants;
import in.notepop.server.exceptions.BaseException;
import in.notepop.server.exceptions.ErrorCodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        User userObj;
        if (user.isEmpty()) {
            userObj = User.getNormalUser(username);
            userRepository.save(userObj);
        } else {
            userObj = user.get();
        }
        return userObj;
    }

    public boolean loginAdmin(String username, String password) {

        if (username.equals(SecurityConstants.ADMIN_USER_NAME) && password.equals(SecurityConstants.ADMIN_PASSWORD))
            return true;
        if (username.equals(SecurityConstants.ADMIN_USER_NAME))
            throw new BaseException(ErrorCodes.INVALID_ADMIN_CREDS);

        return false;
    }

}
