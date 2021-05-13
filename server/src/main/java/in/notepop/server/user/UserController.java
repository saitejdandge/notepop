package in.notepop.server.user;


import in.notepop.server.ResponseWrapper;
import in.notepop.server.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/user")
public class UserController {

    private final UserService userService;

    @Autowired
    UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseWrapper<Session> login(@RequestParam(value = "uniqueId", required = true) String uniqueId) {
        return ResponseWrapper.success(userService.login(uniqueId));
    }
}
