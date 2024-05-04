package springbootpj.clinicpj.controllers;


import springbootpj.clinicpj.dtos.*;
import springbootpj.clinicpj.entities.PasswordResetToken;
import springbootpj.clinicpj.entities.User;
import springbootpj.clinicpj.exception.UserNotFoundException;
import springbootpj.clinicpj.helpers.Utils;
import springbootpj.clinicpj.repositories.PasswordResetTokenRepository;
import springbootpj.clinicpj.services.AuthenticationService;
import springbootpj.clinicpj.services.JwtService;
import springbootpj.clinicpj.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private final JwtService jwtService;
    private final AuthenticationService authenticationService;
    private final UserService userService;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private JavaMailSender javaMailSender;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthenticationController(JwtService jwtService,
                                    AuthenticationService authenticationService,
                                    UserService userService,
                                    JavaMailSender javaMailSender,
                                    PasswordResetTokenRepository passwordResetTokenRepository,
                                    PasswordEncoder passwordEncoder) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
        this.userService = userService;
        this.javaMailSender = javaMailSender;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/users")
    public ResponseEntity<GenericResponse> register(@RequestBody RegisterUserDto registerUserDto) {
        User user = userService.findByEmail(registerUserDto.getEmail());
        if (user == null) {
            User registeredUser = authenticationService.signup(registerUserDto);
            if (registeredUser != null) {
                return ResponseEntity.ok(new GenericResponse(
                        HttpStatus.OK.value(),
                        "Your account registered successfully!",
                        Utils.getTimeStampHelper()));
            }
            throw new UsernameNotFoundException("The passwords are not match. Try again!");
        } else {
            throw new UserNotFoundException("User has registered already - " + registerUserDto.getEmail());
        }
    }

    @PostMapping("/session")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDto loginUserDto) {
        LoginResponse loginResponse = authenticationService.authenticate(loginUserDto);
        return ResponseEntity.ok(loginResponse);
    }


    @PostMapping("/users/emailConfirmation")
    public ResponseEntity<GenericResponse> confirmEmail(@RequestParam String email) {
        User user = userService.findByEmail(email);
        if (user != null) {
//            Generating Token
            PasswordResetToken token = new PasswordResetToken();
            token.setToken(jwtService.generateToken(user));
            token.setUser(user);
            token.setExpiryDate(LocalDateTime.now().plusHours(8));
            passwordResetTokenRepository.save(token);

//            Sending to email
            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setTo("huybqfx18856@funix.edu.vn");
            msg.setSubject("Complete Password Reset!");
            msg.setFrom("test-email@gmail.com");
            msg.setText("To complete the password reset process, please click here: " +
                    "http://localhost:8005/auth/users/passwordReset?token=" + token.getToken());
            javaMailSender.send(msg);
            return ResponseEntity.ok(new GenericResponse(HttpStatus.OK.value(),
                    "Sent to Email successfully!", Utils.getTimeStampHelper()));
        } else {
            throw new UserNotFoundException("Failed to send to Email!");
        }
    }

    @PostMapping("/users/passwordReset")
    public ResponseEntity<GenericResponse> changePassword(@RequestBody PasswordDto passwordDto) {
        PasswordResetToken passwordResetToken = userService.findPasswordResetTokenByToken(passwordDto.getToken());
        if (passwordResetToken != null) {
            User user = passwordResetToken.getUser();
            if (user != null && passwordDto.getPassword().equals(passwordDto.getConfirmingPassword())) {
                user.setPassword(passwordEncoder.encode(passwordDto.getPassword()));
                userService.save(user);
                return ResponseEntity.ok(new GenericResponse(HttpStatus.OK.value(),
                        "Password was updated successfully!", Utils.getTimeStampHelper()));
            } else {
                throw new UserNotFoundException("The passwords are not match!");
            }
        } else {
            throw new UserNotFoundException("The token is not existed or the expiry date is invalid");
        }
    }


}
