package ar.edu.unq.desapp.grupoF.desappgrupoFbackend.controller;

import ar.edu.unq.desapp.grupoF.desappgrupoFbackend.model.User;
import ar.edu.unq.desapp.grupoF.desappgrupoFbackend.model.dto.NewUserDto;
import ar.edu.unq.desapp.grupoF.desappgrupoFbackend.model.login.Login;
import ar.edu.unq.desapp.grupoF.desappgrupoFbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;


@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST})
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users/register")
    public User signUp(@RequestBody @Valid NewUserDto anUser){
        System.out.println("registrando usuario");
        System.out.println(anUser);
        //String json = httpEntity.getBody();
        return userService.signUp(anUser);
    }

    @PostMapping("/users/sessions")
    public User signIn(@RequestBody @Valid Login anUser){
        System.out.println("--------LOGIN-------");
        System.out.println(anUser);
        return userService.signIn(anUser);
    }

    @RequestMapping("/users")
    public List<NewUserDto> getAllUsers() {


        List<User> users = userService.getAll();
        List<NewUserDto> userDtos= new ArrayList<>();

        for(User e: users){
            System.out.println(e);
            userDtos.add(new NewUserDto(e));
        }

        return userDtos;

    }
}
