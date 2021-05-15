package in.notepop.server.user;


import in.notepop.server.shared.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "user")
public class UserController extends BaseController {

    @Autowired
    UserController(UserService userService) {
    }

}
