package ar.edu.unq.desapp.grupoF.desappgrupoFbackend.service;

import ar.edu.unq.desapp.grupoF.desappgrupoFbackend.model.User;
import ar.edu.unq.desapp.grupoF.desappgrupoFbackend.model.dto.NewUserDto;
import ar.edu.unq.desapp.grupoF.desappgrupoFbackend.model.exceptions.EmailExistsException;
import ar.edu.unq.desapp.grupoF.desappgrupoFbackend.model.exceptions.LoginErrorException;
import ar.edu.unq.desapp.grupoF.desappgrupoFbackend.model.login.Login;
import ar.edu.unq.desapp.grupoF.desappgrupoFbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component("userService")
@Transactional
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public User signUp(NewUserDto anUser) {
        if (emailExist(anUser.getEmail())) {
            throw new EmailExistsException(
                    "There is an account with that email: " + anUser.getEmail());
        }
        User user = new User(anUser.getEmail(), anUser.getName(), anUser.getLastName(), anUser.getPassword(), anUser.getBirthDate());
        userRepository.save(user);
        return user;
    }

    @Transactional
    boolean emailExist(String email) {
        return userRepository.findByEmail(email) != null;
    }

    @Transactional
    public User signIn(Login aLogin) {
        User user = userRepository.findByEmail(aLogin.getEmail());
        boolean correctLogin = user != null && aLogin.getPassword().equals(user.getPassword());
        if(correctLogin){
            return user;
        }
        else {
            throw new LoginErrorException("Incorrect email or password");
        }
    }
}