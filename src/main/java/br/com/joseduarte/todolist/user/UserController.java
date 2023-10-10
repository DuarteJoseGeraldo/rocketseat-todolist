package br.com.joseduarte.todolist.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private IUserRepository userRepository;

    @PostMapping("/")
    public ResponseEntity<?> create(@RequestBody UserModel user) {
        if (!Objects.isNull(this.userRepository.findByUsername(user.getUsername())))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User already registered");
        return ResponseEntity.status(HttpStatus.CREATED).body(this.userRepository.save(user));
    }


}
