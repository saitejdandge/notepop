package in.notepop.server.user;


import in.notepop.server.ResponseWrapper;
import in.notepop.server.config.AuthRequest;
import in.notepop.server.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "user")
public class UserController {

    private final UserService userService;

    @Autowired
    UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseWrapper<Session> login(@RequestBody AuthRequest authRequest) {
        try {
            return ResponseWrapper.success(userService.login(authRequest));
        } catch (Exception e) {
            e.printStackTrace();
            if (e instanceof BadCredentialsException)
                return ResponseWrapper.error("Invalid username or password");
            else
                return ResponseWrapper.error(e.getLocalizedMessage());
        }
    }
}
