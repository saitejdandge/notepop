package in.notepop.server.user;


import in.notepop.server.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/user")
public class UserController {

    private final UserService userService;

    @Autowired
    UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public Session login(@RequestParam(value = "uniqueId", required = true) String uniqueId) {
        return userService.login(uniqueId);
    }
}
