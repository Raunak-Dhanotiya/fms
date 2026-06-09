package com.fms.app.userController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.fms.app.payload.RegisterRequest;
import com.fms.app.userModels.User;
import com.fms.app.userRepository.IUserRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/auth")
public class RegisterController {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest request) {

        if (userRepository.existsByUserName(request.getEmail())) {

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Username already exists");
        }

        User user = new User();

        user.setUserName(request.getEmail());
        user.setUserPwd(passwordEncoder.encode(request.getPassword()));
        user.setUserFullName(request.getFullName());
        user.setRoleName(request.getRole());
        user.setIsActive(true);

        userRepository.save(user);

        return ResponseEntity.ok("User registered successfully");
    }
}
