package org.surja.digital_commerce_backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.surja.digital_commerce_backend.dto.ResponseDTO;
import org.surja.digital_commerce_backend.dto.SignUpDTO;
import org.surja.digital_commerce_backend.dto.SignUpResponseDTO;
import org.surja.digital_commerce_backend.dto.UserDTO;
import org.surja.digital_commerce_backend.entity.Role;
import org.surja.digital_commerce_backend.entity.User;
import org.surja.digital_commerce_backend.exception.NotFoundException;
import org.surja.digital_commerce_backend.repo.UserRepo;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class PublicService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    private TokenService tokenService;


    //method to verify the user email
    public SignUpResponseDTO signUpRequest(SignUpDTO signUpDTO) throws NotFoundException {
        // 1. we have to check if the user exist of not
        User user =userRepo.findByEmail(signUpDTO.getEmail());
        if( user != null){
            throw new NotFoundException("User all ready exist !");
        }

        // 2. we need to verify the email
        // creating token
        String token = tokenService.generateToken(signUpDTO.getEmail(),10);

        // 3. triggering  a verification link in email
        String link = "http://localhost:8080/public/verify/"+token;
        // now  we need a email triggering system to sent to email
        // as of now we are sending the link as a response

        return SignUpResponseDTO.builder()
                .message("verification link sent")
                .link(link)
                .build();
    }

    // method to add a user with verified email
    @Transactional
    public ResponseDTO createUser(String token , UserDTO userDTO) throws NotFoundException {

        String value = tokenService.validateToken(token);

        if (value == null || !value.equals(userDTO.getEmail())) {
            throw new NotFoundException(" Invalid Token");
        }

        User user = User.builder()
                .name(userDTO.getName())
                .email(userDTO.getEmail())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .role(Role.CUSTOMER)
                .build();


        userRepo.save(user);
        tokenService.invalidateToken(token);

        return ResponseDTO.builder()
                .message("user successfully created")
                .code("123-S")
                .build();

    }



// method to update the password (Forgot password)
    public SignUpResponseDTO requestPasswordReset(SignUpDTO signUpDTO) throws NotFoundException {
        String email = signUpDTO.getEmail();
        if (userRepo.findByEmail(email) == null) {
            throw new NotFoundException("No user found with email " + email);
        }

        String token = tokenService.generateToken( email, 10);
        String link = "http://localhost:8080/public/reset-password/" + token;

        return SignUpResponseDTO.builder()
                .message("reset link sent")
                .link(link)
                .build();
    }

    @Transactional
    public ResponseDTO resetPassword(String token , UserDTO userDTO) throws NotFoundException {
        String value = tokenService.validateToken(token);
        if (value == null || !value.equals(userDTO.getEmail())) {
            throw new NotFoundException("Invalid token");
        }

        User user = userRepo.findByEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userRepo.save(user);

        tokenService.invalidateToken(token);

        return ResponseDTO.builder().message("password updated successfully").build();
    }
}
