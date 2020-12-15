package softuni.workshop.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import softuni.workshop.service.services.UserService;
import softuni.workshop.web.models.UserRegisterModel;

@Controller
@RequestMapping("/users")
public class UserController extends BaseController {
    //TODO

    @Autowired
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    //@RequestMapping(method = RequestMethod.GET,path = "/users/register")
    @GetMapping("/register")
    public ModelAndView register(){
        return new ModelAndView("user/register");
    }

    @GetMapping("/login")
    public ModelAndView login(){
        return new ModelAndView("user/login");
    }

    @PostMapping("/register")
    public ModelAndView registerConfirm(UserRegisterModel userRegisterModel){
            if (!userRegisterModel.getPassword().equals(userRegisterModel.getConfirmPassword())){
                return super.redirect("/users/register");
            }
            userService.registerUser(userRegisterModel);
            return super.redirect("/users/login");
    }
}
